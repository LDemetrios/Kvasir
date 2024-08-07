// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import org.ldemetrios.kvasir.syntax.psi.*;

public interface TypstTypes {

  IElementType ADD_ASSIGN_EXPR = new TypstElementType("ADD_ASSIGN_EXPR");
  IElementType ADD_EXPR = new TypstElementType("ADD_EXPR");
  IElementType AND_EXPR = new TypstElementType("AND_EXPR");
  IElementType ASSIGN_EXPR = new TypstElementType("ASSIGN_EXPR");
  IElementType ATOMIC = new TypstElementType("ATOMIC");
  IElementType CHAINED_IDENT = new TypstElementType("CHAINED_IDENT");
  IElementType CLOSURE = new TypstElementType("CLOSURE");
  IElementType CODE_BLOCK = new TypstElementType("CODE_BLOCK");
  IElementType CODE_EXPR = new TypstElementType("CODE_EXPR");
  IElementType CODE_LINE = new TypstElementType("CODE_LINE");
  IElementType CODE_SEQUENCE = new TypstElementType("CODE_SEQUENCE");
  IElementType CONDITIONAL = new TypstElementType("CONDITIONAL");
  IElementType CONTENT_BLOCK = new TypstElementType("CONTENT_BLOCK");
  IElementType CONTEXT_EXPR = new TypstElementType("CONTEXT_EXPR");
  IElementType DIV_ASSIGN_EXPR = new TypstElementType("DIV_ASSIGN_EXPR");
  IElementType DIV_EXPR = new TypstElementType("DIV_EXPR");
  IElementType ELSE_EXPR = new TypstElementType("ELSE_EXPR");
  IElementType EMBEDDED_CODE = new TypstElementType("EMBEDDED_CODE");
  IElementType EMB_ATOMIC = new TypstElementType("EMB_ATOMIC");
  IElementType EMB_CODE_LINE = new TypstElementType("EMB_CODE_LINE");
  IElementType EMB_CONDITIONAL = new TypstElementType("EMB_CONDITIONAL");
  IElementType EMB_ELSE_EXPR = new TypstElementType("EMB_ELSE_EXPR");
  IElementType EMB_FIELD_ACCESS = new TypstElementType("EMB_FIELD_ACCESS");
  IElementType EMB_FUNCTION_CALL = new TypstElementType("EMB_FUNCTION_CALL");
  IElementType EMB_METHOD_CALL = new TypstElementType("EMB_METHOD_CALL");
  IElementType EMPH = new TypstElementType("EMPH");
  IElementType EMPTY_DICT = new TypstElementType("EMPTY_DICT");
  IElementType EMPTY_PARENS = new TypstElementType("EMPTY_PARENS");
  IElementType ENTRY = new TypstElementType("ENTRY");
  IElementType EQUALS_EXPR = new TypstElementType("EQUALS_EXPR");
  IElementType EQUATION = new TypstElementType("EQUATION");
  IElementType EXPR = new TypstElementType("EXPR");
  IElementType FIELD_ACCESS = new TypstElementType("FIELD_ACCESS");
  IElementType FOR_BINDING = new TypstElementType("FOR_BINDING");
  IElementType FOR_EXPR = new TypstElementType("FOR_EXPR");
  IElementType FUNCTION_CALL = new TypstElementType("FUNCTION_CALL");
  IElementType FUNCTION_CALL_CONTENT_ONLY = new TypstElementType("FUNCTION_CALL_CONTENT_ONLY");
  IElementType FUNCTION_CALL_REGULAR = new TypstElementType("FUNCTION_CALL_REGULAR");
  IElementType GREATER_EXPR = new TypstElementType("GREATER_EXPR");
  IElementType GREATER_OR_EQ_EXPR = new TypstElementType("GREATER_OR_EQ_EXPR");
  IElementType IDENTIFIER = new TypstElementType("IDENTIFIER");
  IElementType IMPORT_ALL = new TypstElementType("IMPORT_ALL");
  IElementType IMPORT_ITEMS = new TypstElementType("IMPORT_ITEMS");
  IElementType IMPORT_STMT = new TypstElementType("IMPORT_STMT");
  IElementType INCLUDE_STMT = new TypstElementType("INCLUDE_STMT");
  IElementType IN_EXPR = new TypstElementType("IN_EXPR");
  IElementType JUST_TEXT = new TypstElementType("JUST_TEXT");
  IElementType LESS_EXPR = new TypstElementType("LESS_EXPR");
  IElementType LESS_OR_EQ_EXPR = new TypstElementType("LESS_OR_EQ_EXPR");
  IElementType LET_BINDING = new TypstElementType("LET_BINDING");
  IElementType LET_STMT = new TypstElementType("LET_STMT");
  IElementType MARKUP_SEQUENCE = new TypstElementType("MARKUP_SEQUENCE");
  IElementType MATH_EXPRESSION_LITERAL = new TypstElementType("MATH_EXPRESSION_LITERAL");
  IElementType METHOD_CALL = new TypstElementType("METHOD_CALL");
  IElementType METHOD_CALL_CONTENT_ONLY = new TypstElementType("METHOD_CALL_CONTENT_ONLY");
  IElementType METHOD_CALL_REGULAR = new TypstElementType("METHOD_CALL_REGULAR");
  IElementType MINUS_EXPR = new TypstElementType("MINUS_EXPR");
  IElementType MULTILINE_WSPACE = new TypstElementType("MULTILINE_WSPACE");
  IElementType MUL_ASSIGN_EXPR = new TypstElementType("MUL_ASSIGN_EXPR");
  IElementType MUL_EXPR = new TypstElementType("MUL_EXPR");
  IElementType NOT_EXPR = new TypstElementType("NOT_EXPR");
  IElementType NOT_IN_EXPR = new TypstElementType("NOT_IN_EXPR");
  IElementType OR_EXPR = new TypstElementType("OR_EXPR");
  IElementType PARAGRAPH = new TypstElementType("PARAGRAPH");
  IElementType PARENTHESIZED_ELEMENT = new TypstElementType("PARENTHESIZED_ELEMENT");
  IElementType PARENTHESIZED_THING = new TypstElementType("PARENTHESIZED_THING");
  IElementType PLUS_EXPR = new TypstElementType("PLUS_EXPR");
  IElementType PRIMARY_LITERAL = new TypstElementType("PRIMARY_LITERAL");
  IElementType RAW = new TypstElementType("RAW");
  IElementType REFERENCE = new TypstElementType("REFERENCE");
  IElementType RENAMED_IDENT = new TypstElementType("RENAMED_IDENT");
  IElementType RETURN_EXPR = new TypstElementType("RETURN_EXPR");
  IElementType SET_STMT = new TypstElementType("SET_STMT");
  IElementType SHOW_STMT = new TypstElementType("SHOW_STMT");
  IElementType SINGLE_ARG_CLOSURE = new TypstElementType("SINGLE_ARG_CLOSURE");
  IElementType SOFT_IDENT = new TypstElementType("SOFT_IDENT");
  IElementType STRONG = new TypstElementType("STRONG");
  IElementType SUB_ASSIGN_EXPR = new TypstElementType("SUB_ASSIGN_EXPR");
  IElementType SUB_EXPR = new TypstElementType("SUB_EXPR");
  IElementType UNEQUALS_EXPR = new TypstElementType("UNEQUALS_EXPR");
  IElementType VARNAME = new TypstElementType("VARNAME");
  IElementType WHILE_EXPR = new TypstElementType("WHILE_EXPR");
  IElementType WSPACE = new TypstElementType("WSPACE");

  IElementType AND = new TypstTokenType("AND");
  IElementType ARROW = new TypstTokenType("ARROW");
  IElementType AS = new TypstTokenType("AS");
  IElementType AUTO = new TypstTokenType("AUTO");
  IElementType BLOCKY_RAW_CODE = new TypstTokenType("BLOCKY_RAW_CODE");
  IElementType BOOL = new TypstTokenType("BOOL");
  IElementType BREAK = new TypstTokenType("BREAK");
  IElementType CODE_NEW_LINE_SPACE = new TypstTokenType("CODE_NEW_LINE_SPACE");
  IElementType CODE_SPACE = new TypstTokenType("CODE_SPACE");
  IElementType COLON = new TypstTokenType("COLON");
  IElementType COMMA = new TypstTokenType("COMMA");
  IElementType CONTEXT = new TypstTokenType("CONTEXT");
  IElementType CONTINUE = new TypstTokenType("CONTINUE");
  IElementType DOLLAR = new TypstTokenType("DOLLAR");
  IElementType DOT = new TypstTokenType("DOT");
  IElementType DOTS = new TypstTokenType("DOTS");
  IElementType ELSE = new TypstTokenType("ELSE");
  IElementType ENUM_MARKER = new TypstTokenType("ENUM_MARKER");
  IElementType EQ = new TypstTokenType("EQ");
  IElementType EQ_EQ = new TypstTokenType("EQ_EQ");
  IElementType ESCAPE = new TypstTokenType("ESCAPE");
  IElementType EXCL_EQ = new TypstTokenType("EXCL_EQ");
  IElementType FLOAT = new TypstTokenType("FLOAT");
  IElementType FOR = new TypstTokenType("FOR");
  IElementType GT = new TypstTokenType("GT");
  IElementType GT_EQ = new TypstTokenType("GT_EQ");
  IElementType HASH = new TypstTokenType("HASH");
  IElementType HAT = new TypstTokenType("HAT");
  IElementType HEADING_MARKER = new TypstTokenType("HEADING_MARKER");
  IElementType HYPH_EQ = new TypstTokenType("HYPH_EQ");
  IElementType IDENT = new TypstTokenType("IDENT");
  IElementType IF = new TypstTokenType("IF");
  IElementType IMPORT = new TypstTokenType("IMPORT");
  IElementType IN = new TypstTokenType("IN");
  IElementType INCLUDE = new TypstTokenType("INCLUDE");
  IElementType INT = new TypstTokenType("INT");
  IElementType LABEL = new TypstTokenType("LABEL");
  IElementType LEFT_BRACE = new TypstTokenType("LEFT_BRACE");
  IElementType LEFT_BRACKET = new TypstTokenType("LEFT_BRACKET");
  IElementType LEFT_PAREN = new TypstTokenType("LEFT_PAREN");
  IElementType LET = new TypstTokenType("LET");
  IElementType LINEBREAK = new TypstTokenType("LINEBREAK");
  IElementType LINK = new TypstTokenType("LINK");
  IElementType LIST_MARKER = new TypstTokenType("LIST_MARKER");
  IElementType LT = new TypstTokenType("LT");
  IElementType LT_EQ = new TypstTokenType("LT_EQ");
  IElementType MATH_ALIGN_POINT = new TypstTokenType("MATH_ALIGN_POINT");
  IElementType MATH_IDENT = new TypstTokenType("MATH_IDENT");
  IElementType MATH_SHORTHAND = new TypstTokenType("MATH_SHORTHAND");
  IElementType MINUS = new TypstTokenType("MINUS");
  IElementType NEW_LINE = new TypstTokenType("NEW_LINE");
  IElementType NONE = new TypstTokenType("NONE");
  IElementType NOT = new TypstTokenType("NOT");
  IElementType NUMERIC = new TypstTokenType("NUMERIC");
  IElementType OR = new TypstTokenType("OR");
  IElementType PARBREAK = new TypstTokenType("PARBREAK");
  IElementType PLUS = new TypstTokenType("PLUS");
  IElementType PLUS_EQ = new TypstTokenType("PLUS_EQ");
  IElementType PRIME = new TypstTokenType("PRIME");
  IElementType RAW_CODE = new TypstTokenType("RAW_CODE");
  IElementType RAW_DELIM = new TypstTokenType("RAW_DELIM");
  IElementType REF_MARKER = new TypstTokenType("REF_MARKER");
  IElementType RETURN = new TypstTokenType("RETURN");
  IElementType RIGHT_BRACE = new TypstTokenType("RIGHT_BRACE");
  IElementType RIGHT_BRACKET = new TypstTokenType("RIGHT_BRACKET");
  IElementType RIGHT_PAREN = new TypstTokenType("RIGHT_PAREN");
  IElementType ROOT = new TypstTokenType("ROOT");
  IElementType SEMICOLON = new TypstTokenType("SEMICOLON");
  IElementType SET = new TypstTokenType("SET");
  IElementType SHORTHAND = new TypstTokenType("SHORTHAND");
  IElementType SHOW = new TypstTokenType("SHOW");
  IElementType SLASH = new TypstTokenType("SLASH");
  IElementType SLASH_EQ = new TypstTokenType("SLASH_EQ");
  IElementType SMART_QUOTE = new TypstTokenType("SMART_QUOTE");
  IElementType SPACE = new TypstTokenType("SPACE");
  IElementType STAR = new TypstTokenType("STAR");
  IElementType STAR_EQ = new TypstTokenType("STAR_EQ");
  IElementType STRING = new TypstTokenType("STRING");
  IElementType TERM_MARKER = new TypstTokenType("TERM_MARKER");
  IElementType TEXT = new TypstTokenType("TEXT");
  IElementType UNDERSCORE = new TypstTokenType("UNDERSCORE");
  IElementType WHILE = new TypstTokenType("WHILE");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ADD_ASSIGN_EXPR) {
        return new TypstAddAssignExprImpl(node);
      }
      else if (type == ADD_EXPR) {
        return new TypstAddExprImpl(node);
      }
      else if (type == AND_EXPR) {
        return new TypstAndExprImpl(node);
      }
      else if (type == ASSIGN_EXPR) {
        return new TypstAssignExprImpl(node);
      }
      else if (type == ATOMIC) {
        return new TypstAtomicImpl(node);
      }
      else if (type == CHAINED_IDENT) {
        return new TypstChainedIdentImpl(node);
      }
      else if (type == CLOSURE) {
        return new TypstClosureImpl(node);
      }
      else if (type == CODE_BLOCK) {
        return new TypstCodeBlockImpl(node);
      }
      else if (type == CODE_EXPR) {
        return new TypstCodeExprImpl(node);
      }
      else if (type == CODE_LINE) {
        return new TypstCodeLineImpl(node);
      }
      else if (type == CODE_SEQUENCE) {
        return new TypstCodeSequenceImpl(node);
      }
      else if (type == CONDITIONAL) {
        return new TypstConditionalImpl(node);
      }
      else if (type == CONTENT_BLOCK) {
        return new TypstContentBlockImpl(node);
      }
      else if (type == CONTEXT_EXPR) {
        return new TypstContextExprImpl(node);
      }
      else if (type == DIV_ASSIGN_EXPR) {
        return new TypstDivAssignExprImpl(node);
      }
      else if (type == DIV_EXPR) {
        return new TypstDivExprImpl(node);
      }
      else if (type == ELSE_EXPR) {
        return new TypstElseExprImpl(node);
      }
      else if (type == EMBEDDED_CODE) {
        return new TypstEmbeddedCodeImpl(node);
      }
      else if (type == EMB_ATOMIC) {
        return new TypstEmbAtomicImpl(node);
      }
      else if (type == EMB_CODE_LINE) {
        return new TypstEmbCodeLineImpl(node);
      }
      else if (type == EMB_CONDITIONAL) {
        return new TypstEmbConditionalImpl(node);
      }
      else if (type == EMB_ELSE_EXPR) {
        return new TypstEmbElseExprImpl(node);
      }
      else if (type == EMB_FIELD_ACCESS) {
        return new TypstEmbFieldAccessImpl(node);
      }
      else if (type == EMB_FUNCTION_CALL) {
        return new TypstEmbFunctionCallImpl(node);
      }
      else if (type == EMB_METHOD_CALL) {
        return new TypstEmbMethodCallImpl(node);
      }
      else if (type == EMPH) {
        return new TypstEmphImpl(node);
      }
      else if (type == EMPTY_DICT) {
        return new TypstEmptyDictImpl(node);
      }
      else if (type == EMPTY_PARENS) {
        return new TypstEmptyParensImpl(node);
      }
      else if (type == ENTRY) {
        return new TypstEntryImpl(node);
      }
      else if (type == EQUALS_EXPR) {
        return new TypstEqualsExprImpl(node);
      }
      else if (type == EQUATION) {
        return new TypstEquationImpl(node);
      }
      else if (type == FIELD_ACCESS) {
        return new TypstFieldAccessImpl(node);
      }
      else if (type == FOR_BINDING) {
        return new TypstForBindingImpl(node);
      }
      else if (type == FOR_EXPR) {
        return new TypstForExprImpl(node);
      }
      else if (type == FUNCTION_CALL) {
        return new TypstFunctionCallImpl(node);
      }
      else if (type == FUNCTION_CALL_CONTENT_ONLY) {
        return new TypstFunctionCallContentOnlyImpl(node);
      }
      else if (type == FUNCTION_CALL_REGULAR) {
        return new TypstFunctionCallRegularImpl(node);
      }
      else if (type == GREATER_EXPR) {
        return new TypstGreaterExprImpl(node);
      }
      else if (type == GREATER_OR_EQ_EXPR) {
        return new TypstGreaterOrEqExprImpl(node);
      }
      else if (type == IDENTIFIER) {
        return new TypstIdentifierImpl(node);
      }
      else if (type == IMPORT_ALL) {
        return new TypstImportAllImpl(node);
      }
      else if (type == IMPORT_ITEMS) {
        return new TypstImportItemsImpl(node);
      }
      else if (type == IMPORT_STMT) {
        return new TypstImportStmtImpl(node);
      }
      else if (type == INCLUDE_STMT) {
        return new TypstIncludeStmtImpl(node);
      }
      else if (type == IN_EXPR) {
        return new TypstInExprImpl(node);
      }
      else if (type == JUST_TEXT) {
        return new TypstJustTextImpl(node);
      }
      else if (type == LESS_EXPR) {
        return new TypstLessExprImpl(node);
      }
      else if (type == LESS_OR_EQ_EXPR) {
        return new TypstLessOrEqExprImpl(node);
      }
      else if (type == LET_BINDING) {
        return new TypstLetBindingImpl(node);
      }
      else if (type == LET_STMT) {
        return new TypstLetStmtImpl(node);
      }
      else if (type == MARKUP_SEQUENCE) {
        return new TypstMarkupSequenceImpl(node);
      }
      else if (type == MATH_EXPRESSION_LITERAL) {
        return new TypstMathExpressionLiteralImpl(node);
      }
      else if (type == METHOD_CALL) {
        return new TypstMethodCallImpl(node);
      }
      else if (type == METHOD_CALL_CONTENT_ONLY) {
        return new TypstMethodCallContentOnlyImpl(node);
      }
      else if (type == METHOD_CALL_REGULAR) {
        return new TypstMethodCallRegularImpl(node);
      }
      else if (type == MINUS_EXPR) {
        return new TypstMinusExprImpl(node);
      }
      else if (type == MULTILINE_WSPACE) {
        return new TypstMultilineWspaceImpl(node);
      }
      else if (type == MUL_ASSIGN_EXPR) {
        return new TypstMulAssignExprImpl(node);
      }
      else if (type == MUL_EXPR) {
        return new TypstMulExprImpl(node);
      }
      else if (type == NOT_EXPR) {
        return new TypstNotExprImpl(node);
      }
      else if (type == NOT_IN_EXPR) {
        return new TypstNotInExprImpl(node);
      }
      else if (type == OR_EXPR) {
        return new TypstOrExprImpl(node);
      }
      else if (type == PARAGRAPH) {
        return new TypstParagraphImpl(node);
      }
      else if (type == PARENTHESIZED_ELEMENT) {
        return new TypstParenthesizedElementImpl(node);
      }
      else if (type == PARENTHESIZED_THING) {
        return new TypstParenthesizedThingImpl(node);
      }
      else if (type == PLUS_EXPR) {
        return new TypstPlusExprImpl(node);
      }
      else if (type == PRIMARY_LITERAL) {
        return new TypstPrimaryLiteralImpl(node);
      }
      else if (type == RAW) {
        return new TypstRawImpl(node);
      }
      else if (type == REFERENCE) {
        return new TypstReferenceImpl(node);
      }
      else if (type == RENAMED_IDENT) {
        return new TypstRenamedIdentImpl(node);
      }
      else if (type == RETURN_EXPR) {
        return new TypstReturnExprImpl(node);
      }
      else if (type == SET_STMT) {
        return new TypstSetStmtImpl(node);
      }
      else if (type == SHOW_STMT) {
        return new TypstShowStmtImpl(node);
      }
      else if (type == SINGLE_ARG_CLOSURE) {
        return new TypstSingleArgClosureImpl(node);
      }
      else if (type == SOFT_IDENT) {
        return new TypstSoftIdentImpl(node);
      }
      else if (type == STRONG) {
        return new TypstStrongImpl(node);
      }
      else if (type == SUB_ASSIGN_EXPR) {
        return new TypstSubAssignExprImpl(node);
      }
      else if (type == SUB_EXPR) {
        return new TypstSubExprImpl(node);
      }
      else if (type == UNEQUALS_EXPR) {
        return new TypstUnequalsExprImpl(node);
      }
      else if (type == VARNAME) {
        return new TypstVarnameImpl(node);
      }
      else if (type == WHILE_EXPR) {
        return new TypstWhileExprImpl(node);
      }
      else if (type == WSPACE) {
        return new TypstWspaceImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
