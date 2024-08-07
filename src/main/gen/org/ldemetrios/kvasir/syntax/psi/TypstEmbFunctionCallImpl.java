// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;

public class TypstEmbFunctionCallImpl extends TypstFunctionCallImpl implements TypstEmbFunctionCall {

  public TypstEmbFunctionCallImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull TypstVisitor visitor) {
    visitor.visitEmbFunctionCall(this);
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
  @Nullable
  public TypstParenthesized getParenthesized() {
    return findChildByClass(TypstParenthesized.class);
  }

}
