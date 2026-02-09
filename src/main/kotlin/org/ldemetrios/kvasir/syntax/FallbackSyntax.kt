package org.ldemetrios.kvasir.syntax

import org.ldemetrios.tyko.compiler.IndexedMark
import org.ldemetrios.tyko.compiler.SyntaxKind
import org.ldemetrios.tyko.compiler.SyntaxMark

internal fun fallbackMarkupMarks(textLength: Int): List<IndexedMark> {
    val length = textLength.coerceAtLeast(0)
    return listOf(
        IndexedMark(SyntaxMark.NodeStart(SyntaxKind.Markup), 0),
        IndexedMark(SyntaxMark.NodeStart(SyntaxKind.Text), 0),
        IndexedMark(SyntaxMark.NodeEnd, length),
        IndexedMark(SyntaxMark.NodeEnd, length),
    )
}
