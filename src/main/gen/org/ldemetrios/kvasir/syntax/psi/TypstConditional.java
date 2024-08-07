// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import java.util.List;
import org.jetbrains.annotations.*;

public interface TypstConditional extends TypstPrimary {

  @Nullable
  TypstCodeBlock getCodeBlock();

  @Nullable
  TypstContentBlock getContentBlock();

  @Nullable
  TypstElseExpr getElseExpr();

  @Nullable
  TypstExpr getExpr();

  @Nullable
  TypstMultilineWspace getMultilineWspace();

  @NotNull
  List<TypstWspace> getWspaceList();

}
