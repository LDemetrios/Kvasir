package org.ldemetrios.kvasir.syntax

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import org.ldemetrios.instance
import org.ldemetrios.tyko.compiler.SyntaxKind
import org.ldemetrios.tyko.compiler.SyntaxMark
import org.ldemetrios.tyko.compiler.SyntaxMode
import org.ldemetrios.tyko.compiler.parseSource

class TypstParser(val mode: SyntaxMode) : PsiParser {
    override fun parse(root: IElementType, builder: PsiBuilder): ASTNode {
        val rootMarker: PsiBuilder.Marker = builder.mark();

        val marks = instance!!.parseSource(builder.originalText.toString(), mode).marks

        var wasLeaf = false
        val stack = mutableListOf<Pair<PsiBuilder.Marker, IElementType>>()
        var toSkip = 0
        for ((mark, idx) in marks) {
            if (toSkip > 0) {
                toSkip--
                continue
            }
            when (mark) {
                is SyntaxMark.Error -> {
                    wasLeaf = true
                    stack.add(builder.mark() to SyntaxKind.Error.nodeType)
                }

                SyntaxMark.NodeEnd -> {
                    val lexeme = if (wasLeaf) {
                        builder.tokenType.also {
                            builder.advanceLexer()
                        }
                    } else null
                    val (opened, elType) = stack.removeLast()
                    val kind = (elType as? TypstElementType)?.kind
                    if (kind == SyntaxKind.Space && lexeme == TYPST_WHITESPACE) {
                        opened.drop()
                    } else {
                        opened.done(elType)
                        if (kind != SyntaxKind.Hash && stack.lastOrNull()?.second === TYPST_EMBEDDED_CODE) {
                            stack.removeLast().first.done(TYPST_EMBEDDED_CODE)
                        }
                    }
                    wasLeaf = false
                }

                is SyntaxMark.NodeStart -> {
                    if (mark.kind == SyntaxKind.Shebang || mark.kind == SyntaxKind.BlockComment || mark.kind == SyntaxKind.LineComment) {
                        toSkip = 1
                        continue
                    }
                    wasLeaf = true
                    if (mark.kind == SyntaxKind.Hash) {
                        stack.add(builder.mark() to TYPST_EMBEDDED_CODE)
                    }
                    if (mark.kind == SyntaxKind.Text && stack.lastOrNull()?.second == SyntaxKind.Raw.nodeType) {
                        stack.add(builder.mark() to TYPST_RAW_TEXT)
                    } else {
                        stack.add(builder.mark() to mark.kind.nodeType)
                    }
                }
            }
        }

        rootMarker.done(root)
        return builder.treeBuilt
    }
}