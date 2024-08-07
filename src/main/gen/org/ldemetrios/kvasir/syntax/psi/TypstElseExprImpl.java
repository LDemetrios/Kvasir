// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;

public class TypstElseExprImpl extends ASTWrapperPsiElement implements TypstElseExpr {

  public TypstElseExprImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull TypstVisitor visitor) {
    visitor.visitElseExpr(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TypstVisitor) accept((TypstVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public TypstCodeBlock getCodeBlock() {
    return findChildByClass(TypstCodeBlock.class);
  }

  @Override
  @Nullable
  public TypstConditional getConditional() {
    return findChildByClass(TypstConditional.class);
  }

  @Override
  @Nullable
  public TypstContentBlock getContentBlock() {
    return findChildByClass(TypstContentBlock.class);
  }

  @Override
  @Nullable
  public TypstWspace getWspace() {
    return findChildByClass(TypstWspace.class);
  }

}
