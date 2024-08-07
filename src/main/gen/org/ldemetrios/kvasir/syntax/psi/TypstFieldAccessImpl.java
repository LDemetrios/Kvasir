// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;

public class TypstFieldAccessImpl extends ASTWrapperPsiElement implements TypstFieldAccess {

  public TypstFieldAccessImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull TypstVisitor visitor) {
    visitor.visitFieldAccess(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TypstVisitor) accept((TypstVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public TypstMultilineWspace getMultilineWspace() {
    return findNotNullChildByClass(TypstMultilineWspace.class);
  }

  @Override
  @NotNull
  public TypstSoftIdent getSoftIdent() {
    return findNotNullChildByClass(TypstSoftIdent.class);
  }

  @Override
  @NotNull
  public TypstWspace getWspace() {
    return findNotNullChildByClass(TypstWspace.class);
  }

}
