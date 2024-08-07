// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import java.util.List;
import org.jetbrains.annotations.*;

public interface TypstForExpr extends TypstPrimary {

  @Nullable
  TypstCodeBlock getCodeBlock();

  @Nullable
  TypstContentBlock getContentBlock();

  @Nullable
  TypstExpr getExpr();

  @Nullable
  TypstForBinding getForBinding();

  @NotNull
  List<TypstWspace> getWspaceList();

}
