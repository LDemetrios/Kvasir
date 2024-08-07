// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.extapi.psi.ASTWrapperPsiElement;

public class TypstForExprImpl extends ASTWrapperPsiElement implements TypstForExpr {

  public TypstForExprImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull TypstVisitor visitor) {
    visitor.visitForExpr(this);
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
  public TypstContentBlock getContentBlock() {
    return findChildByClass(TypstContentBlock.class);
  }

  @Override
  @Nullable
  public TypstExpr getExpr() {
    return findChildByClass(TypstExpr.class);
  }

  @Override
  @Nullable
  public TypstForBinding getForBinding() {
    return findChildByClass(TypstForBinding.class);
  }

  @Override
  @NotNull
  public List<TypstWspace> getWspaceList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, TypstWspace.class);
  }

}