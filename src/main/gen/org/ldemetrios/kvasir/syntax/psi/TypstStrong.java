// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import java.util.List;
import org.jetbrains.annotations.*;

public interface TypstStrong extends TypstMarkupExpression {

  @NotNull
  List<TypstEmbeddedCode> getEmbeddedCodeList();

  @NotNull
  List<TypstEmph> getEmphList();

  @NotNull
  List<TypstEquation> getEquationList();

  @NotNull
  List<TypstJustText> getJustTextList();

  @NotNull
  List<TypstRaw> getRawList();

  @NotNull
  List<TypstReference> getReferenceList();

}
