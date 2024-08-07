// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import java.util.List;
import org.jetbrains.annotations.*;

public interface TypstEmbAtomic extends TypstAtomic {

  @Nullable
  TypstEmbConditional getEmbConditional();

  @NotNull
  List<TypstEmbFieldAccess> getEmbFieldAccessList();

  @NotNull
  List<TypstEmbFunctionCall> getEmbFunctionCallList();

  @NotNull
  List<TypstEmbMethodCall> getEmbMethodCallList();

}
