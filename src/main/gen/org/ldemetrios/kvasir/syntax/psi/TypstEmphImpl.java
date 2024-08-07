// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.extapi.psi.ASTWrapperPsiElement;

public class TypstEmphImpl extends ASTWrapperPsiElement implements TypstEmph {

  public TypstEmphImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull TypstVisitor visitor) {
    visitor.visitEmph(this);
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
  public List<TypstEquation> getEquationList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, TypstEquation.class);
  }

  @Override
  @NotNull
  public List<TypstJustText> getJustTextList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, TypstJustText.class);
  }

  @Override
  @NotNull
  public List<TypstRaw> getRawList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, TypstRaw.class);
  }

  @Override
  @NotNull
  public List<TypstReference> getReferenceList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, TypstReference.class);
  }

  @Override
  @NotNull
  public List<TypstStrong> getStrongList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, TypstStrong.class);
  }

}
