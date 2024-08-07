// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.extapi.psi.ASTWrapperPsiElement;

public class TypstImportStmtImpl extends ASTWrapperPsiElement implements TypstImportStmt {

  public TypstImportStmtImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull TypstVisitor visitor) {
    visitor.visitImportStmt(this);
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
  @Nullable
  public TypstImportAll getImportAll() {
    return findChildByClass(TypstImportAll.class);
  }

  @Override
  @Nullable
  public TypstImportItems getImportItems() {
    return findChildByClass(TypstImportItems.class);
  }

  @Override
  @NotNull
  public List<TypstWspace> getWspaceList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, TypstWspace.class);
  }

}
