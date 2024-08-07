// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface TypstParagraph extends PsiElement {

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

  @NotNull
  List<TypstStrong> getStrongList();

}
