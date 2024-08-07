// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import java.util.List;
import org.jetbrains.annotations.*;

public interface TypstSetStmt extends TypstStmt {

  @Nullable
  TypstChainedIdent getChainedIdent();

  @Nullable
  TypstExpr getExpr();

  @Nullable
  TypstParenthesized getParenthesized();

  @NotNull
  List<TypstWspace> getWspaceList();

}
