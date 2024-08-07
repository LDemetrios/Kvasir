// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;

public class TypstLetBindingImpl extends ASTWrapperPsiElement implements TypstLetBinding {

  public TypstLetBindingImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull TypstVisitor visitor) {
    visitor.visitLetBinding(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TypstVisitor) accept((TypstVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public TypstParenthesized getParenthesized() {
    return findChildByClass(TypstParenthesized.class);
  }

  @Override
  @Nullable
  public TypstVarname getVarname() {
    return findChildByClass(TypstVarname.class);
  }

}
