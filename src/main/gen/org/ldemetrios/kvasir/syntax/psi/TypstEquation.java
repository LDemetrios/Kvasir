// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import java.util.List;
import org.jetbrains.annotations.*;

public interface TypstEquation extends TypstMarkupExpression, TypstPrimary {

  @NotNull
  List<TypstEmbeddedCode> getEmbeddedCodeList();

  @NotNull
  List<TypstMathExpressionLiteral> getMathExpressionLiteralList();

}
