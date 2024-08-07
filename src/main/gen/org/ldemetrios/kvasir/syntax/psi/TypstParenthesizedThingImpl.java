// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;

public class TypstParenthesizedThingImpl extends TypstParenthesizedImpl implements TypstParenthesizedThing {

  public TypstParenthesizedThingImpl(@NotNull ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull TypstVisitor visitor) {
    visitor.visitParenthesizedThing(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof TypstVisitor) accept((TypstVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<TypstParenthesizedElement> getParenthesizedElementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, TypstParenthesizedElement.class);
  }

  @Override
  @Nullable
  public TypstWspace getWspace() {
    return findChildByClass(TypstWspace.class);
  }

}
