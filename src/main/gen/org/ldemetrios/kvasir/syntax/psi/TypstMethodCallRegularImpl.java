// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.extapi.psi.ASTWrapperPsiElement;

public class TypstMethodCallRegularImpl extends ASTWrapperPsiElement implements TypstMethodCallRegular {

  public TypstMethodCallRegularImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull TypstVisitor visitor) {
    visitor.visitMethodCallRegular(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TypstVisitor) accept((TypstVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<TypstContentBlock> getContentBlockList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, TypstContentBlock.class);
  }

  @Override
  @NotNull
  public TypstParenthesized getParenthesized() {
    return findNotNullChildByClass(TypstParenthesized.class);
  }

  @Override
  @NotNull
  public TypstWspace getWspace() {
    return findNotNullChildByClass(TypstWspace.class);
  }

}
