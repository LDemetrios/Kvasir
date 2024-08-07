// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;

public class TypstEmbCodeLineImpl extends TypstCodeLineImpl implements TypstEmbCodeLine {

  public TypstEmbCodeLineImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull TypstVisitor visitor) {
    visitor.visitEmbCodeLine(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TypstVisitor) accept((TypstVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public TypstEmbAtomic getEmbAtomic() {
    return findChildByClass(TypstEmbAtomic.class);
  }

  @Override
  @Nullable
  public TypstWspace getWspace() {
    return findChildByClass(TypstWspace.class);
  }

}
