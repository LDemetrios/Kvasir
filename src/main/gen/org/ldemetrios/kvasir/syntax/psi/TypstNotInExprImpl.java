// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;

public class TypstNotInExprImpl extends TypstBinaryExprImpl implements TypstNotInExpr {

  public TypstNotInExprImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull TypstVisitor visitor) {
    visitor.visitNotInExpr(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TypstVisitor) accept((TypstVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<TypstExpr> getExprList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, TypstExpr.class);
  }

  @Override
  @NotNull
  public List<TypstWspace> getWspaceList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, TypstWspace.class);
  }

}
