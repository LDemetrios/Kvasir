// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.extapi.psi.ASTWrapperPsiElement;

public class TypstEquationImpl extends ASTWrapperPsiElement implements TypstEquation {

  public TypstEquationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull TypstVisitor visitor) {
    visitor.visitEquation(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TypstVisitor) accept((TypstVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<TypstEmbeddedCode> getEmbeddedCodeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, TypstEmbeddedCode.class);
  }

  @Override
  @NotNull
  public List<TypstMathExpressionLiteral> getMathExpressionLiteralList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, TypstMathExpressionLiteral.class);
  }

}
