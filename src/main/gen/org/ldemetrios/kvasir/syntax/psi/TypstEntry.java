// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface TypstEntry extends PsiElement {

  @NotNull
  List<TypstExpr> getExprList();

  @NotNull
  List<TypstWspace> getWspaceList();

}
