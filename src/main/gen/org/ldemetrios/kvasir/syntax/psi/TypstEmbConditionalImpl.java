// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;

public class TypstEmbConditionalImpl extends TypstConditionalImpl implements TypstEmbConditional {

  public TypstEmbConditionalImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull TypstVisitor visitor) {
    visitor.visitEmbConditional(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TypstVisitor) accept((TypstVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public TypstEmbElseExpr getEmbElseExpr() {
    return findChildByClass(TypstEmbElseExpr.class);
  }

}
