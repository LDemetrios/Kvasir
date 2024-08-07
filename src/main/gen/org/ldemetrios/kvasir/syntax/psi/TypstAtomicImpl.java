// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;

public class TypstAtomicImpl extends TypstExprImpl implements TypstAtomic {

  public TypstAtomicImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull TypstVisitor visitor) {
    visitor.visitAtomic(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TypstVisitor) accept((TypstVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public TypstClosure getClosure() {
    return findChildByClass(TypstClosure.class);
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
  public TypstContextExpr getContextExpr() {
    return findChildByClass(TypstContextExpr.class);
  }

  @Override
  @Nullable
  public TypstEquation getEquation() {
    return findChildByClass(TypstEquation.class);
  }

  @Override
  @NotNull
  public List<TypstFieldAccess> getFieldAccessList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, TypstFieldAccess.class);
  }

  @Override
  @Nullable
  public TypstForExpr getForExpr() {
    return findChildByClass(TypstForExpr.class);
  }

  @Override
  @NotNull
  public List<TypstFunctionCall> getFunctionCallList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, TypstFunctionCall.class);
  }

  @Override
  @Nullable
  public TypstIdentifier getIdentifier() {
    return findChildByClass(TypstIdentifier.class);
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
  @NotNull
  public List<TypstMethodCall> getMethodCallList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, TypstMethodCall.class);
  }

  @Override
  @Nullable
  public TypstParenthesized getParenthesized() {
    return findChildByClass(TypstParenthesized.class);
  }

  @Override
  @Nullable
  public TypstPrimaryLiteral getPrimaryLiteral() {
    return findChildByClass(TypstPrimaryLiteral.class);
  }

  @Override
  @Nullable
  public TypstRaw getRaw() {
    return findChildByClass(TypstRaw.class);
  }

  @Override
  @Nullable
  public TypstReturnExpr getReturnExpr() {
    return findChildByClass(TypstReturnExpr.class);
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
  @Nullable
  public TypstSingleArgClosure getSingleArgClosure() {
    return findChildByClass(TypstSingleArgClosure.class);
  }

  @Override
  @Nullable
  public TypstWhileExpr getWhileExpr() {
    return findChildByClass(TypstWhileExpr.class);
  }

}
