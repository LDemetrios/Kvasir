// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;

public class TypstVisitor extends PsiElementVisitor {

  public void visitAddAssignExpr(@NotNull TypstAddAssignExpr o) {
    visitBinaryExpr(o);
  }

  public void visitAddExpr(@NotNull TypstAddExpr o) {
    visitBinaryExpr(o);
  }

  public void visitAndExpr(@NotNull TypstAndExpr o) {
    visitBinaryExpr(o);
  }

  public void visitAssignExpr(@NotNull TypstAssignExpr o) {
    visitBinaryExpr(o);
  }

  public void visitAtomic(@NotNull TypstAtomic o) {
    visitExpr(o);
  }

  public void visitBinaryExpr(@NotNull TypstBinaryExpr o) {
    visitExpr(o);
  }

  public void visitChainedIdent(@NotNull TypstChainedIdent o) {
    visitPsiElement(o);
  }

  public void visitClosure(@NotNull TypstClosure o) {
    visitPrimary(o);
  }

  public void visitCodeBlock(@NotNull TypstCodeBlock o) {
    visitPrimary(o);
  }

  public void visitCodeExpr(@NotNull TypstCodeExpr o) {
    visitCodeLine(o);
  }

  public void visitCodeLine(@NotNull TypstCodeLine o) {
    visitPsiElement(o);
  }

  public void visitCodeSequence(@NotNull TypstCodeSequence o) {
    visitPsiElement(o);
  }

  public void visitConditional(@NotNull TypstConditional o) {
    visitPrimary(o);
  }

  public void visitContentBlock(@NotNull TypstContentBlock o) {
    visitPrimary(o);
  }

  public void visitContextExpr(@NotNull TypstContextExpr o) {
    visitStmt(o);
  }

  public void visitDivAssignExpr(@NotNull TypstDivAssignExpr o) {
    visitBinaryExpr(o);
  }

  public void visitDivExpr(@NotNull TypstDivExpr o) {
    visitBinaryExpr(o);
  }

  public void visitElseExpr(@NotNull TypstElseExpr o) {
    visitPsiElement(o);
  }

  public void visitEmbAtomic(@NotNull TypstEmbAtomic o) {
    visitAtomic(o);
  }

  public void visitEmbCodeLine(@NotNull TypstEmbCodeLine o) {
    visitCodeLine(o);
  }

  public void visitEmbConditional(@NotNull TypstEmbConditional o) {
    visitConditional(o);
  }

  public void visitEmbElseExpr(@NotNull TypstEmbElseExpr o) {
    visitElseExpr(o);
  }

  public void visitEmbFieldAccess(@NotNull TypstEmbFieldAccess o) {
    visitFieldAccess(o);
  }

  public void visitEmbFunctionCall(@NotNull TypstEmbFunctionCall o) {
    visitFunctionCall(o);
  }

  public void visitEmbMethodCall(@NotNull TypstEmbMethodCall o) {
    visitMethodCall(o);
  }

  public void visitEmbeddedCode(@NotNull TypstEmbeddedCode o) {
    visitMarkupExpression(o);
    // visitMathExpression(o);
  }

  public void visitEmph(@NotNull TypstEmph o) {
    visitMarkupExpression(o);
  }

  public void visitEmptyDict(@NotNull TypstEmptyDict o) {
    visitParenthesized(o);
  }

  public void visitEmptyParens(@NotNull TypstEmptyParens o) {
    visitParenthesized(o);
  }

  public void visitEntry(@NotNull TypstEntry o) {
    visitPsiElement(o);
  }

  public void visitEqualsExpr(@NotNull TypstEqualsExpr o) {
    visitBinaryExpr(o);
  }

  public void visitEquation(@NotNull TypstEquation o) {
    visitMarkupExpression(o);
    // visitPrimary(o);
  }

  public void visitExpr(@NotNull TypstExpr o) {
    visitPsiElement(o);
  }

  public void visitFieldAccess(@NotNull TypstFieldAccess o) {
    visitPsiElement(o);
  }

  public void visitForBinding(@NotNull TypstForBinding o) {
    visitPsiElement(o);
  }

  public void visitForExpr(@NotNull TypstForExpr o) {
    visitPrimary(o);
  }

  public void visitFunctionCall(@NotNull TypstFunctionCall o) {
    visitPsiElement(o);
  }

  public void visitFunctionCallContentOnly(@NotNull TypstFunctionCallContentOnly o) {
    visitPsiElement(o);
  }

  public void visitFunctionCallRegular(@NotNull TypstFunctionCallRegular o) {
    visitPsiElement(o);
  }

  public void visitGreaterExpr(@NotNull TypstGreaterExpr o) {
    visitBinaryExpr(o);
  }

  public void visitGreaterOrEqExpr(@NotNull TypstGreaterOrEqExpr o) {
    visitBinaryExpr(o);
  }

  public void visitIdentifier(@NotNull TypstIdentifier o) {
    visitPrimary(o);
  }

  public void visitImportAll(@NotNull TypstImportAll o) {
    visitPsiElement(o);
  }

  public void visitImportItems(@NotNull TypstImportItems o) {
    visitPsiElement(o);
  }

  public void visitImportStmt(@NotNull TypstImportStmt o) {
    visitStmt(o);
  }

  public void visitInExpr(@NotNull TypstInExpr o) {
    visitBinaryExpr(o);
  }

  public void visitIncludeStmt(@NotNull TypstIncludeStmt o) {
    visitStmt(o);
  }

  public void visitJustText(@NotNull TypstJustText o) {
    visitMarkupExpression(o);
  }

  public void visitLessExpr(@NotNull TypstLessExpr o) {
    visitBinaryExpr(o);
  }

  public void visitLessOrEqExpr(@NotNull TypstLessOrEqExpr o) {
    visitBinaryExpr(o);
  }

  public void visitLetBinding(@NotNull TypstLetBinding o) {
    visitPsiElement(o);
  }

  public void visitLetStmt(@NotNull TypstLetStmt o) {
    visitStmt(o);
  }

  public void visitMarkupExpression(@NotNull TypstMarkupExpression o) {
    visitPsiElement(o);
  }

  public void visitMarkupSequence(@NotNull TypstMarkupSequence o) {
    visitPsiElement(o);
  }

  public void visitMathExpression(@NotNull TypstMathExpression o) {
    visitPsiElement(o);
  }

  public void visitMathExpressionLiteral(@NotNull TypstMathExpressionLiteral o) {
    visitMathExpression(o);
  }

  public void visitMethodCall(@NotNull TypstMethodCall o) {
    visitPsiElement(o);
  }

  public void visitMethodCallContentOnly(@NotNull TypstMethodCallContentOnly o) {
    visitPsiElement(o);
  }

  public void visitMethodCallRegular(@NotNull TypstMethodCallRegular o) {
    visitPsiElement(o);
  }

  public void visitMinusExpr(@NotNull TypstMinusExpr o) {
    visitUnaryExpr(o);
  }

  public void visitMulAssignExpr(@NotNull TypstMulAssignExpr o) {
    visitBinaryExpr(o);
  }

  public void visitMulExpr(@NotNull TypstMulExpr o) {
    visitBinaryExpr(o);
  }

  public void visitMultilineWspace(@NotNull TypstMultilineWspace o) {
    visitPsiElement(o);
  }

  public void visitNotExpr(@NotNull TypstNotExpr o) {
    visitUnaryExpr(o);
  }

  public void visitNotInExpr(@NotNull TypstNotInExpr o) {
    visitBinaryExpr(o);
  }

  public void visitOrExpr(@NotNull TypstOrExpr o) {
    visitBinaryExpr(o);
  }

  public void visitParagraph(@NotNull TypstParagraph o) {
    visitPsiElement(o);
  }

  public void visitParenthesized(@NotNull TypstParenthesized o) {
    visitPrimary(o);
  }

  public void visitParenthesizedElement(@NotNull TypstParenthesizedElement o) {
    visitPsiElement(o);
  }

  public void visitParenthesizedThing(@NotNull TypstParenthesizedThing o) {
    visitParenthesized(o);
  }

  public void visitPlusExpr(@NotNull TypstPlusExpr o) {
    visitUnaryExpr(o);
  }

  public void visitPrimary(@NotNull TypstPrimary o) {
    visitPsiElement(o);
  }

  public void visitPrimaryLiteral(@NotNull TypstPrimaryLiteral o) {
    visitPrimary(o);
  }

  public void visitRaw(@NotNull TypstRaw o) {
    visitPrimary(o);
    // visitMarkupExpression(o);
  }

  public void visitReference(@NotNull TypstReference o) {
    visitMarkupExpression(o);
  }

  public void visitRenamedIdent(@NotNull TypstRenamedIdent o) {
    visitPsiElement(o);
  }

  public void visitReturnExpr(@NotNull TypstReturnExpr o) {
    visitPrimary(o);
  }

  public void visitSetStmt(@NotNull TypstSetStmt o) {
    visitStmt(o);
  }

  public void visitShowStmt(@NotNull TypstShowStmt o) {
    visitStmt(o);
  }

  public void visitSingleArgClosure(@NotNull TypstSingleArgClosure o) {
    visitPrimary(o);
  }

  public void visitSoftIdent(@NotNull TypstSoftIdent o) {
    visitPsiElement(o);
  }

  public void visitStmt(@NotNull TypstStmt o) {
    visitPrimary(o);
  }

  public void visitStrong(@NotNull TypstStrong o) {
    visitMarkupExpression(o);
  }

  public void visitSubAssignExpr(@NotNull TypstSubAssignExpr o) {
    visitBinaryExpr(o);
  }

  public void visitSubExpr(@NotNull TypstSubExpr o) {
    visitBinaryExpr(o);
  }

  public void visitUnaryExpr(@NotNull TypstUnaryExpr o) {
    visitExpr(o);
  }

  public void visitUnequalsExpr(@NotNull TypstUnequalsExpr o) {
    visitBinaryExpr(o);
  }

  public void visitVarname(@NotNull TypstVarname o) {
    visitPsiElement(o);
  }

  public void visitWhileExpr(@NotNull TypstWhileExpr o) {
    visitPrimary(o);
  }

  public void visitWspace(@NotNull TypstWspace o) {
    visitPsiElement(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}
