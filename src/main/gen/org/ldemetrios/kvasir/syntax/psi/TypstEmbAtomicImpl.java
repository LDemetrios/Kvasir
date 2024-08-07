// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;

public class TypstEmbAtomicImpl extends TypstAtomicImpl implements TypstEmbAtomic {

  public TypstEmbAtomicImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull TypstVisitor visitor) {
    visitor.visitEmbAtomic(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TypstVisitor) accept((TypstVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public TypstEmbConditional getEmbConditional() {
    return findChildByClass(TypstEmbConditional.class);
  }

  @Override
  @NotNull
  public List<TypstEmbFieldAccess> getEmbFieldAccessList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, TypstEmbFieldAccess.class);
  }

  @Override
  @NotNull
  public List<TypstEmbFunctionCall> getEmbFunctionCallList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, TypstEmbFunctionCall.class);
  }

  @Override
  @NotNull
  public List<TypstEmbMethodCall> getEmbMethodCallList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, TypstEmbMethodCall.class);
  }

}
