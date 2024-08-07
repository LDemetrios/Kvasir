// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import java.util.List;
import org.jetbrains.annotations.*;

public interface TypstClosure extends TypstPrimary {

  @NotNull
  TypstExpr getExpr();

  @NotNull
  TypstParenthesized getParenthesized();

  @NotNull
  List<TypstWspace> getWspaceList();

}
