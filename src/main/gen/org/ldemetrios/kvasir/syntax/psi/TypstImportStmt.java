// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import java.util.List;
import org.jetbrains.annotations.*;

public interface TypstImportStmt extends TypstStmt {

  @Nullable
  TypstExpr getExpr();

  @Nullable
  TypstImportAll getImportAll();

  @Nullable
  TypstImportItems getImportItems();

  @NotNull
  List<TypstWspace> getWspaceList();

}