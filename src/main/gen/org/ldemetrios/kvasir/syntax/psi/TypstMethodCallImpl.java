// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;

public class TypstMethodCallImpl extends ASTWrapperPsiElement implements TypstMethodCall {

  public TypstMethodCallImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull TypstVisitor visitor) {
    visitor.visitMethodCall(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TypstVisitor) accept((TypstVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public TypstMethodCallContentOnly getMethodCallContentOnly() {
    return findChildByClass(TypstMethodCallContentOnly.class);
  }

  @Override
  @Nullable
  public TypstMethodCallRegular getMethodCallRegular() {
    return findChildByClass(TypstMethodCallRegular.class);
  }

  @Override
  @NotNull
  public TypstMultilineWspace getMultilineWspace() {
    return findNotNullChildByClass(TypstMultilineWspace.class);
  }

}
