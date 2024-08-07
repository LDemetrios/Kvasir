// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;

public class TypstPlusExprImpl extends TypstUnaryExprImpl implements TypstPlusExpr {

  public TypstPlusExprImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull TypstVisitor visitor) {
    visitor.visitPlusExpr(this);
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