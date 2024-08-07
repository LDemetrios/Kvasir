// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax.psi;

import java.util.List;
import org.jetbrains.annotations.*;

public interface TypstAtomic extends TypstExpr {

  @Nullable
  TypstClosure getClosure();

  @Nullable
  TypstCodeBlock getCodeBlock();

  @Nullable
  TypstConditional getConditional();

  @Nullable
  TypstContentBlock getContentBlock();

  @Nullable
  TypstContextExpr getContextExpr();

  @Nullable
  TypstEquation getEquation();

  @NotNull
  List<TypstFieldAccess> getFieldAccessList();

  @Nullable
  TypstForExpr getForExpr();

  @NotNull
  List<TypstFunctionCall> getFunctionCallList();

  @Nullable
  TypstIdentifier getIdentifier();

  @Nullable
  TypstImportStmt getImportStmt();

  @Nullable
  TypstIncludeStmt getIncludeStmt();

  @Nullable
  TypstLetStmt getLetStmt();

  @NotNull
  List<TypstMethodCall> getMethodCallList();

  @Nullable
  TypstParenthesized getParenthesized();

  @Nullable
  TypstPrimaryLiteral getPrimaryLiteral();

  @Nullable
  TypstRaw getRaw();

  @Nullable
  TypstReturnExpr getReturnExpr();

  @Nullable
  TypstSetStmt getSetStmt();

  @Nullable
  TypstShowStmt getShowStmt();

  @Nullable
  TypstSingleArgClosure getSingleArgClosure();

  @Nullable
  TypstWhileExpr getWhileExpr();

}
