// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface TypstParenthesizedElement extends PsiElement {

  @Nullable
  TypstEntry getEntry();

  @Nullable
  TypstExpr getExpr();

  @NotNull
  List<TypstWspace> getWspaceList();

}
