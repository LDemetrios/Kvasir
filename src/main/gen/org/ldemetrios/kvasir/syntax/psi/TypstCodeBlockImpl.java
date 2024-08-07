// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;

public class TypstCodeBlockImpl extends ASTWrapperPsiElement implements TypstCodeBlock {

  public TypstCodeBlockImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull TypstVisitor visitor) {
    visitor.visitCodeBlock(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TypstVisitor) accept((TypstVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public TypstCodeSequence getCodeSequence() {
    return findChildByClass(TypstCodeSequence.class);
  }

  @Override
  @Nullable
  public TypstWspace getWspace() {
    return findChildByClass(TypstWspace.class);
  }

}
