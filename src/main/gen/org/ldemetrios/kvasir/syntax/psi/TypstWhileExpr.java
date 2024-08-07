// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import java.util.List;
import org.jetbrains.annotations.*;

public interface TypstWhileExpr extends TypstPrimary {

  @Nullable
  TypstCodeBlock getCodeBlock();

  @Nullable
  TypstContentBlock getContentBlock();

  @Nullable
  TypstExpr getExpr();

  @NotNull
  List<TypstWspace> getWspaceList();

}
