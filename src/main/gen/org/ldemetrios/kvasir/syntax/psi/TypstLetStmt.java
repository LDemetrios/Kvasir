// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import java.util.List;
import org.jetbrains.annotations.*;

public interface TypstLetStmt extends TypstStmt {

  @Nullable
  TypstExpr getExpr();

  @Nullable
  TypstLetBinding getLetBinding();

  @NotNull
  List<TypstWspace> getWspaceList();

}
