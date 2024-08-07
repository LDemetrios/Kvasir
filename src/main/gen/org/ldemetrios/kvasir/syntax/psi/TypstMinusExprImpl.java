// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;

public class TypstMinusExprImpl extends TypstUnaryExprImpl implements TypstMinusExpr {

  public TypstMinusExprImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull TypstVisitor visitor) {
    visitor.visitMinusExpr(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TypstVisitor) accept((TypstVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public TypstExpr getExpr() {
    return findChildByClass(TypstExpr.class);
  }

  @Override
  @NotNull
  public TypstWspace getWspace() {
    return findNotNullChildByClass(TypstWspace.class);
  }

}
