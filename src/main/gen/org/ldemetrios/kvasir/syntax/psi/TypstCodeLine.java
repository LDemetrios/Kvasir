// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface TypstCodeLine extends PsiElement {

  @Nullable
  TypstContextExpr getContextExpr();

  @Nullable
  TypstExpr getExpr();

  @Nullable
  TypstImportStmt getImportStmt();

  @Nullable
  TypstIncludeStmt getIncludeStmt();

  @Nullable
  TypstLetStmt getLetStmt();

  @Nullable
  TypstSetStmt getSetStmt();

  @Nullable
  TypstShowStmt getShowStmt();

  @NotNull
  List<TypstWspace> getWspaceList();

}
