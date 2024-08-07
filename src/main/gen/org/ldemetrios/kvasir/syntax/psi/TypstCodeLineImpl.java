// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.extapi.psi.ASTWrapperPsiElement;

public class TypstCodeLineImpl extends ASTWrapperPsiElement implements TypstCodeLine {

  public TypstCodeLineImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull TypstVisitor visitor) {
    visitor.visitCodeLine(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TypstVisitor) accept((TypstVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public TypstContextExpr getContextExpr() {
    return findChildByClass(TypstContextExpr.class);
  }

  @Override
  @Nullable
  public TypstExpr getExpr() {
    return findChildByClass(TypstExpr.class);
  }

  @Override
  @Nullable
  public TypstImportStmt getImportStmt() {
    return findChildByClass(TypstImportStmt.class);
  }

  @Override
  @Nullable
  public TypstIncludeStmt getIncludeStmt() {
    return findChildByClass(TypstIncludeStmt.class);
  }

  @Override
  @Nullable
  public TypstLetStmt getLetStmt() {
    return findChildByClass(TypstLetStmt.class);
  }

  @Override
  @Nullable
  public TypstSetStmt getSetStmt() {
    return findChildByClass(TypstSetStmt.class);
  }

  @Override
  @Nullable
  public TypstShowStmt getShowStmt() {
    return findChildByClass(TypstShowStmt.class);
  }

  @Override
  @NotNull
  public List<TypstWspace> getWspaceList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, TypstWspace.class);
  }

}
