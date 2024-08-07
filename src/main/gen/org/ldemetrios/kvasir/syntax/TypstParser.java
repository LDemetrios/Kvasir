// This is a generated file. Not intended for manual editing.
package org.ldemetrios.kvasir.syntax;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static org.ldemetrios.kvasir.syntax.TypstTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class TypstParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, EXTENDS_SETS_);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return root(b, l + 1);
  }

  public static final TokenSet[] EXTENDS_SETS_ = new TokenSet[] {
    create_token_set_(CONDITIONAL, EMB_CONDITIONAL),
    create_token_set_(ELSE_EXPR, EMB_ELSE_EXPR),
    create_token_set_(EMB_METHOD_CALL, METHOD_CALL),
    create_token_set_(EMB_FIELD_ACCESS, FIELD_ACCESS),
    create_token_set_(EMB_FUNCTION_CALL, FUNCTION_CALL),
    create_token_set_(CODE_EXPR, CODE_LINE, EMB_CODE_LINE),
    create_token_set_(EMPTY_DICT, EMPTY_PARENS, PARENTHESIZED_THING),
    create_token_set_(ADD_ASSIGN_EXPR, ADD_EXPR, AND_EXPR, ASSIGN_EXPR,
      ATOMIC, DIV_ASSIGN_EXPR, DIV_EXPR, EMB_ATOMIC,
      EQUALS_EXPR, EXPR, GREATER_EXPR, GREATER_OR_EQ_EXPR,
      IN_EXPR, LESS_EXPR, LESS_OR_EQ_EXPR, MINUS_EXPR,
      MUL_ASSIGN_EXPR, MUL_EXPR, NOT_EXPR, NOT_IN_EXPR,
      OR_EXPR, PLUS_EXPR, SUB_ASSIGN_EXPR, SUB_EXPR,
      UNEQUALS_EXPR),
  };

  /* ********************************************************** */
  // IDENT (DOT IDENT)*
  public static boolean chained_ident(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "chained_ident")) return false;
    if (!nextTokenIs(b, IDENT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENT);
    r = r && chained_ident_1(b, l + 1);
    exit_section_(b, m, CHAINED_IDENT, r);
    return r;
  }

  // (DOT IDENT)*
  private static boolean chained_ident_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "chained_ident_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!chained_ident_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "chained_ident_1", c)) break;
    }
    return true;
  }

  // DOT IDENT
  private static boolean chained_ident_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "chained_ident_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, DOT, IDENT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // wspace ARROW wspace expr
  public static boolean closure(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "closure")) return false;
    if (!nextTokenIs(b, "<closure>", ARROW, CODE_SPACE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _LEFT_, CLOSURE, "<closure>");
    r = wspace(b, l + 1);
    r = r && consumeToken(b, ARROW);
    r = r && wspace(b, l + 1);
    r = r && expr(b, l + 1, -1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // LEFT_BRACE code_sequence wspace RIGHT_BRACE
  public static boolean code_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "code_block")) return false;
    if (!nextTokenIs(b, LEFT_BRACE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CODE_BLOCK, null);
    r = consumeToken(b, LEFT_BRACE);
    p = r; // pin = 1
    r = r && report_error_(b, code_sequence(b, l + 1));
    r = p && report_error_(b, wspace(b, l + 1)) && r;
    r = p && consumeToken(b, RIGHT_BRACE) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // code_expr_inside
  public static boolean code_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "code_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CODE_EXPR, "<code expr>");
    r = code_expr_inside(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // wspace (stmt_disjoint | expr | context_expr) wspace
  static boolean code_expr_inside(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "code_expr_inside")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = wspace(b, l + 1);
    r = r && code_expr_inside_1(b, l + 1);
    r = r && wspace(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // stmt_disjoint | expr | context_expr
  private static boolean code_expr_inside_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "code_expr_inside_1")) return false;
    boolean r;
    r = stmt_disjoint(b, l + 1);
    if (!r) r = expr(b, l + 1, -1);
    if (!r) r = context_expr(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // (code_expr_inside | wspace)? code_line_sep
  public static boolean code_line(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "code_line")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CODE_LINE, "<code line>");
    r = code_line_0(b, l + 1);
    r = r && code_line_sep(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (code_expr_inside | wspace)?
  private static boolean code_line_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "code_line_0")) return false;
    code_line_0_0(b, l + 1);
    return true;
  }

  // code_expr_inside | wspace
  private static boolean code_line_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "code_line_0_0")) return false;
    boolean r;
    r = code_expr_inside(b, l + 1);
    if (!r) r = wspace(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // CODE_NEW_LINE_SPACE | SEMICOLON
  static boolean code_line_sep(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "code_line_sep")) return false;
    if (!nextTokenIs(b, "", CODE_NEW_LINE_SPACE, SEMICOLON)) return false;
    boolean r;
    r = consumeToken(b, CODE_NEW_LINE_SPACE);
    if (!r) r = consumeToken(b, SEMICOLON);
    return r;
  }

  /* ********************************************************** */
  // code_line* code_expr?
  public static boolean code_sequence(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "code_sequence")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CODE_SEQUENCE, "<code sequence>");
    r = code_sequence_0(b, l + 1);
    r = r && code_sequence_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // code_line*
  private static boolean code_sequence_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "code_sequence_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!code_line(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "code_sequence_0", c)) break;
    }
    return true;
  }

  // code_expr?
  private static boolean code_sequence_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "code_sequence_1")) return false;
    code_expr(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // IF wspace expr wspace (code_block | content_block) (multiline_wspace else_expr)?
  public static boolean conditional(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "conditional")) return false;
    if (!nextTokenIs(b, IF)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CONDITIONAL, null);
    r = consumeToken(b, IF);
    p = r; // pin = 1
    r = r && report_error_(b, wspace(b, l + 1));
    r = p && report_error_(b, expr(b, l + 1, -1)) && r;
    r = p && report_error_(b, wspace(b, l + 1)) && r;
    r = p && report_error_(b, conditional_4(b, l + 1)) && r;
    r = p && conditional_5(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // code_block | content_block
  private static boolean conditional_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "conditional_4")) return false;
    boolean r;
    r = code_block(b, l + 1);
    if (!r) r = content_block(b, l + 1);
    return r;
  }

  // (multiline_wspace else_expr)?
  private static boolean conditional_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "conditional_5")) return false;
    conditional_5_0(b, l + 1);
    return true;
  }

  // multiline_wspace else_expr
  private static boolean conditional_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "conditional_5_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = multiline_wspace(b, l + 1);
    r = r && else_expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // LEFT_BRACKET markup_sequence RIGHT_BRACKET
  public static boolean content_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "content_block")) return false;
    if (!nextTokenIs(b, LEFT_BRACKET)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CONTENT_BLOCK, null);
    r = consumeToken(b, LEFT_BRACKET);
    p = r; // pin = 1
    r = r && report_error_(b, markup_sequence(b, l + 1));
    r = p && consumeToken(b, RIGHT_BRACKET) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // CONTEXT wspace expr
  public static boolean context_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "context_expr")) return false;
    if (!nextTokenIs(b, CONTEXT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CONTEXT_EXPR, null);
    r = consumeToken(b, CONTEXT);
    p = r; // pin = 1
    r = r && report_error_(b, wspace(b, l + 1));
    r = p && expr(b, l + 1, -1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // ELSE wspace (conditional | code_block | content_block)
  public static boolean else_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "else_expr")) return false;
    if (!nextTokenIs(b, ELSE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ELSE_EXPR, null);
    r = consumeToken(b, ELSE);
    p = r; // pin = 1
    r = r && report_error_(b, wspace(b, l + 1));
    r = p && else_expr_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // conditional | code_block | content_block
  private static boolean else_expr_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "else_expr_2")) return false;
    boolean r;
    r = conditional(b, l + 1);
    if (!r) r = code_block(b, l + 1);
    if (!r) r = content_block(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // emb_primary (emb_method_call | emb_field_access | emb_function_call)*
  public static boolean emb_atomic(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emb_atomic")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, EMB_ATOMIC, "<emb atomic>");
    r = emb_primary(b, l + 1);
    p = r; // pin = 1
    r = r && emb_atomic_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (emb_method_call | emb_field_access | emb_function_call)*
  private static boolean emb_atomic_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emb_atomic_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!emb_atomic_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "emb_atomic_1", c)) break;
    }
    return true;
  }

  // emb_method_call | emb_field_access | emb_function_call
  private static boolean emb_atomic_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emb_atomic_1_0")) return false;
    boolean r;
    r = emb_method_call(b, l + 1);
    if (!r) r = emb_field_access(b, l + 1);
    if (!r) r = emb_function_call(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // (stmt_disjoint | emb_atomic)? (wspace SEMICOLON)?
  public static boolean emb_code_line(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emb_code_line")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EMB_CODE_LINE, "<emb code line>");
    r = emb_code_line_0(b, l + 1);
    r = r && emb_code_line_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (stmt_disjoint | emb_atomic)?
  private static boolean emb_code_line_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emb_code_line_0")) return false;
    emb_code_line_0_0(b, l + 1);
    return true;
  }

  // stmt_disjoint | emb_atomic
  private static boolean emb_code_line_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emb_code_line_0_0")) return false;
    boolean r;
    r = stmt_disjoint(b, l + 1);
    if (!r) r = emb_atomic(b, l + 1);
    return r;
  }

  // (wspace SEMICOLON)?
  private static boolean emb_code_line_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emb_code_line_1")) return false;
    emb_code_line_1_0(b, l + 1);
    return true;
  }

  // wspace SEMICOLON
  private static boolean emb_code_line_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emb_code_line_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = wspace(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // IF wspace expr wspace (code_block | content_block) wspace emb_else_expr?
  public static boolean emb_conditional(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emb_conditional")) return false;
    if (!nextTokenIs(b, IF)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, EMB_CONDITIONAL, null);
    r = consumeToken(b, IF);
    p = r; // pin = 1
    r = r && report_error_(b, wspace(b, l + 1));
    r = p && report_error_(b, expr(b, l + 1, -1)) && r;
    r = p && report_error_(b, wspace(b, l + 1)) && r;
    r = p && report_error_(b, emb_conditional_4(b, l + 1)) && r;
    r = p && report_error_(b, wspace(b, l + 1)) && r;
    r = p && emb_conditional_6(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // code_block | content_block
  private static boolean emb_conditional_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emb_conditional_4")) return false;
    boolean r;
    r = code_block(b, l + 1);
    if (!r) r = content_block(b, l + 1);
    return r;
  }

  // emb_else_expr?
  private static boolean emb_conditional_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emb_conditional_6")) return false;
    emb_else_expr(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ELSE wspace (emb_conditional | code_block | content_block)
  public static boolean emb_else_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emb_else_expr")) return false;
    if (!nextTokenIs(b, ELSE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, EMB_ELSE_EXPR, null);
    r = consumeToken(b, ELSE);
    p = r; // pin = 1
    r = r && report_error_(b, wspace(b, l + 1));
    r = p && emb_else_expr_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // emb_conditional | code_block | content_block
  private static boolean emb_else_expr_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emb_else_expr_2")) return false;
    boolean r;
    r = emb_conditional(b, l + 1);
    if (!r) r = code_block(b, l + 1);
    if (!r) r = content_block(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // wspace DOT soft_ident
  public static boolean emb_field_access(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emb_field_access")) return false;
    if (!nextTokenIs(b, "<emb field access>", CODE_SPACE, DOT)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EMB_FIELD_ACCESS, "<emb field access>");
    r = wspace(b, l + 1);
    r = r && consumeToken(b, DOT);
    r = r && soft_ident(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // emb_function_call_regular | emb_function_call_content_only
  public static boolean emb_function_call(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emb_function_call")) return false;
    if (!nextTokenIs(b, "<emb function call>", LEFT_BRACKET, LEFT_PAREN)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EMB_FUNCTION_CALL, "<emb function call>");
    r = emb_function_call_regular(b, l + 1);
    if (!r) r = emb_function_call_content_only(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // content_block+
  static boolean emb_function_call_content_only(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emb_function_call_content_only")) return false;
    if (!nextTokenIs(b, LEFT_BRACKET)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = content_block(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!content_block(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "emb_function_call_content_only", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // parenthesized_disjoint content_block*
  static boolean emb_function_call_regular(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emb_function_call_regular")) return false;
    if (!nextTokenIs(b, LEFT_PAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parenthesized_disjoint(b, l + 1);
    r = r && emb_function_call_regular_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // content_block*
  private static boolean emb_function_call_regular_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emb_function_call_regular_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!content_block(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "emb_function_call_regular_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // multiline_wspace (emb_method_call_regular | emb_method_call_content_only)
  public static boolean emb_method_call(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emb_method_call")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EMB_METHOD_CALL, "<emb method call>");
    r = multiline_wspace(b, l + 1);
    r = r && emb_method_call_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // emb_method_call_regular | emb_method_call_content_only
  private static boolean emb_method_call_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emb_method_call_1")) return false;
    boolean r;
    r = emb_method_call_regular(b, l + 1);
    if (!r) r = emb_method_call_content_only(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // DOT IDENT content_block+
  static boolean emb_method_call_content_only(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emb_method_call_content_only")) return false;
    if (!nextTokenIs(b, DOT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, DOT, IDENT);
    r = r && emb_method_call_content_only_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // content_block+
  private static boolean emb_method_call_content_only_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emb_method_call_content_only_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = content_block(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!content_block(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "emb_method_call_content_only_2", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // DOT IDENT parenthesized_disjoint content_block*
  static boolean emb_method_call_regular(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emb_method_call_regular")) return false;
    if (!nextTokenIs(b, DOT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, DOT, IDENT);
    r = r && parenthesized_disjoint(b, l + 1);
    r = r && emb_method_call_regular_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // content_block*
  private static boolean emb_method_call_regular_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emb_method_call_regular_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!content_block(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "emb_method_call_regular_3", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // code_block | content_block | parenthesized_disjoint | raw | equation | emb_conditional |
  //     while_expr | return_expr | context_expr | for_expr | primary_literal
  static boolean emb_primary(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emb_primary")) return false;
    boolean r;
    r = code_block(b, l + 1);
    if (!r) r = content_block(b, l + 1);
    if (!r) r = parenthesized_disjoint(b, l + 1);
    if (!r) r = raw(b, l + 1);
    if (!r) r = equation(b, l + 1);
    if (!r) r = emb_conditional(b, l + 1);
    if (!r) r = while_expr(b, l + 1);
    if (!r) r = return_expr(b, l + 1);
    if (!r) r = context_expr(b, l + 1);
    if (!r) r = for_expr(b, l + 1);
    if (!r) r = primary_literal(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // HASH emb_code_line
  public static boolean embedded_code(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "embedded_code")) return false;
    if (!nextTokenIs(b, HASH)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, EMBEDDED_CODE, null);
    r = consumeToken(b, HASH);
    p = r; // pin = 1
    r = r && emb_code_line(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // UNDERSCORE (markup_expression_except_emph_strong | strong)* UNDERSCORE?
  public static boolean emph(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emph")) return false;
    if (!nextTokenIs(b, UNDERSCORE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, EMPH, null);
    r = consumeToken(b, UNDERSCORE);
    p = r; // pin = 1
    r = r && report_error_(b, emph_1(b, l + 1));
    r = p && emph_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (markup_expression_except_emph_strong | strong)*
  private static boolean emph_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emph_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!emph_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "emph_1", c)) break;
    }
    return true;
  }

  // markup_expression_except_emph_strong | strong
  private static boolean emph_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emph_1_0")) return false;
    boolean r;
    r = markup_expression_except_emph_strong(b, l + 1);
    if (!r) r = strong(b, l + 1);
    return r;
  }

  // UNDERSCORE?
  private static boolean emph_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "emph_2")) return false;
    consumeToken(b, UNDERSCORE);
    return true;
  }

  /* ********************************************************** */
  // LEFT_PAREN wspace COLON wspace RIGHT_PAREN
  public static boolean empty_dict(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "empty_dict")) return false;
    if (!nextTokenIs(b, LEFT_PAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LEFT_PAREN);
    r = r && wspace(b, l + 1);
    r = r && consumeToken(b, COLON);
    r = r && wspace(b, l + 1);
    r = r && consumeToken(b, RIGHT_PAREN);
    exit_section_(b, m, EMPTY_DICT, r);
    return r;
  }

  /* ********************************************************** */
  // LEFT_PAREN wspace RIGHT_PAREN
  public static boolean empty_parens(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "empty_parens")) return false;
    if (!nextTokenIs(b, LEFT_PAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LEFT_PAREN);
    r = r && wspace(b, l + 1);
    r = r && consumeToken(b, RIGHT_PAREN);
    exit_section_(b, m, EMPTY_PARENS, r);
    return r;
  }

  /* ********************************************************** */
  // wspace COLON wspace expr
  public static boolean entry(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "entry")) return false;
    if (!nextTokenIs(b, "<entry>", CODE_SPACE, COLON)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _LEFT_, ENTRY, "<entry>");
    r = wspace(b, l + 1);
    r = r && consumeToken(b, COLON);
    p = r; // pin = 2
    r = r && report_error_(b, wspace(b, l + 1));
    r = p && expr(b, l + 1, -1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // DOLLAR (math_expression_literal | embedded_code )* DOLLAR
  public static boolean equation(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "equation")) return false;
    if (!nextTokenIs(b, DOLLAR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DOLLAR);
    r = r && equation_1(b, l + 1);
    r = r && consumeToken(b, DOLLAR);
    exit_section_(b, m, EQUATION, r);
    return r;
  }

  // (math_expression_literal | embedded_code )*
  private static boolean equation_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "equation_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!equation_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "equation_1", c)) break;
    }
    return true;
  }

  // math_expression_literal | embedded_code
  private static boolean equation_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "equation_1_0")) return false;
    boolean r;
    r = math_expression_literal(b, l + 1);
    if (!r) r = embedded_code(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // parenthesized_disjoint closure?
  static boolean expr_with_parens(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expr_with_parens")) return false;
    if (!nextTokenIs(b, LEFT_PAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parenthesized_disjoint(b, l + 1);
    r = r && expr_with_parens_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // closure?
  private static boolean expr_with_parens_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expr_with_parens_1")) return false;
    closure(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // multiline_wspace DOT wspace soft_ident
  public static boolean field_access(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_access")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FIELD_ACCESS, "<field access>");
    r = multiline_wspace(b, l + 1);
    r = r && consumeToken(b, DOT);
    r = r && wspace(b, l + 1);
    r = r && soft_ident(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // parenthesized_disjoint | IDENT | UNDERSCORE
  public static boolean for_binding(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_binding")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FOR_BINDING, "<for binding>");
    r = parenthesized_disjoint(b, l + 1);
    if (!r) r = consumeToken(b, IDENT);
    if (!r) r = consumeToken(b, UNDERSCORE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // FOR wspace for_binding wspace IN wspace expr wspace (code_block | content_block)
  public static boolean for_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_expr")) return false;
    if (!nextTokenIs(b, FOR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, FOR_EXPR, null);
    r = consumeToken(b, FOR);
    p = r; // pin = 1
    r = r && report_error_(b, wspace(b, l + 1));
    r = p && report_error_(b, for_binding(b, l + 1)) && r;
    r = p && report_error_(b, wspace(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, IN)) && r;
    r = p && report_error_(b, wspace(b, l + 1)) && r;
    r = p && report_error_(b, expr(b, l + 1, -1)) && r;
    r = p && report_error_(b, wspace(b, l + 1)) && r;
    r = p && for_expr_8(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // code_block | content_block
  private static boolean for_expr_8(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "for_expr_8")) return false;
    boolean r;
    r = code_block(b, l + 1);
    if (!r) r = content_block(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // function_call_regular | function_call_content_only
  public static boolean function_call(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_call")) return false;
    if (!nextTokenIs(b, "<function call>", LEFT_BRACKET, LEFT_PAREN)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FUNCTION_CALL, "<function call>");
    r = function_call_regular(b, l + 1);
    if (!r) r = function_call_content_only(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // content_block+
  public static boolean function_call_content_only(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_call_content_only")) return false;
    if (!nextTokenIs(b, LEFT_BRACKET)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = content_block(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!content_block(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "function_call_content_only", c)) break;
    }
    exit_section_(b, m, FUNCTION_CALL_CONTENT_ONLY, r);
    return r;
  }

  /* ********************************************************** */
  // parenthesized_disjoint content_block*
  public static boolean function_call_regular(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_call_regular")) return false;
    if (!nextTokenIs(b, LEFT_PAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parenthesized_disjoint(b, l + 1);
    r = r && function_call_regular_1(b, l + 1);
    exit_section_(b, m, FUNCTION_CALL_REGULAR, r);
    return r;
  }

  // content_block*
  private static boolean function_call_regular_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_call_regular_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!content_block(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "function_call_regular_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // IDENT | UNDERSCORE
  public static boolean identifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "identifier")) return false;
    if (!nextTokenIs(b, "<identifier>", IDENT, UNDERSCORE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, IDENTIFIER, "<identifier>");
    r = consumeToken(b, IDENT);
    if (!r) r = consumeToken(b, UNDERSCORE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // COLON wspace STAR
  public static boolean import_all(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "import_all")) return false;
    if (!nextTokenIs(b, COLON)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COLON);
    r = r && wspace(b, l + 1);
    r = r && consumeToken(b, STAR);
    exit_section_(b, m, IMPORT_ALL, r);
    return r;
  }

  /* ********************************************************** */
  // COLON wspace renamed_ident wspace (COMMA wspace renamed_ident)* (wspace COMMA)?
  public static boolean import_items(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "import_items")) return false;
    if (!nextTokenIs(b, COLON)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COLON);
    r = r && wspace(b, l + 1);
    r = r && renamed_ident(b, l + 1);
    r = r && wspace(b, l + 1);
    r = r && import_items_4(b, l + 1);
    r = r && import_items_5(b, l + 1);
    exit_section_(b, m, IMPORT_ITEMS, r);
    return r;
  }

  // (COMMA wspace renamed_ident)*
  private static boolean import_items_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "import_items_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!import_items_4_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "import_items_4", c)) break;
    }
    return true;
  }

  // COMMA wspace renamed_ident
  private static boolean import_items_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "import_items_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && wspace(b, l + 1);
    r = r && renamed_ident(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (wspace COMMA)?
  private static boolean import_items_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "import_items_5")) return false;
    import_items_5_0(b, l + 1);
    return true;
  }

  // wspace COMMA
  private static boolean import_items_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "import_items_5_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = wspace(b, l + 1);
    r = r && consumeToken(b, COMMA);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // IMPORT wspace expr (wspace AS wspace IDENT)? wspace (import_all | import_items)?
  public static boolean import_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "import_stmt")) return false;
    if (!nextTokenIs(b, IMPORT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, IMPORT_STMT, null);
    r = consumeToken(b, IMPORT);
    p = r; // pin = 1
    r = r && report_error_(b, wspace(b, l + 1));
    r = p && report_error_(b, expr(b, l + 1, -1)) && r;
    r = p && report_error_(b, import_stmt_3(b, l + 1)) && r;
    r = p && report_error_(b, wspace(b, l + 1)) && r;
    r = p && import_stmt_5(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (wspace AS wspace IDENT)?
  private static boolean import_stmt_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "import_stmt_3")) return false;
    import_stmt_3_0(b, l + 1);
    return true;
  }

  // wspace AS wspace IDENT
  private static boolean import_stmt_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "import_stmt_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = wspace(b, l + 1);
    r = r && consumeToken(b, AS);
    r = r && wspace(b, l + 1);
    r = r && consumeToken(b, IDENT);
    exit_section_(b, m, null, r);
    return r;
  }

  // (import_all | import_items)?
  private static boolean import_stmt_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "import_stmt_5")) return false;
    import_stmt_5_0(b, l + 1);
    return true;
  }

  // import_all | import_items
  private static boolean import_stmt_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "import_stmt_5_0")) return false;
    boolean r;
    r = import_all(b, l + 1);
    if (!r) r = import_items(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // INCLUDE wspace expr
  public static boolean include_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "include_stmt")) return false;
    if (!nextTokenIs(b, INCLUDE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, INCLUDE_STMT, null);
    r = consumeToken(b, INCLUDE);
    p = r; // pin = 1
    r = r && report_error_(b, wspace(b, l + 1));
    r = p && expr(b, l + 1, -1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // (TEXT | SEMICOLON)+ ((SPACE+) (TEXT | SEMICOLON)+)*
  public static boolean just_text(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "just_text")) return false;
    if (!nextTokenIs(b, "<just text>", SEMICOLON, TEXT)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, JUST_TEXT, "<just text>");
    r = just_text_0(b, l + 1);
    r = r && just_text_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (TEXT | SEMICOLON)+
  private static boolean just_text_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "just_text_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = just_text_0_0(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!just_text_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "just_text_0", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // TEXT | SEMICOLON
  private static boolean just_text_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "just_text_0_0")) return false;
    boolean r;
    r = consumeToken(b, TEXT);
    if (!r) r = consumeToken(b, SEMICOLON);
    return r;
  }

  // ((SPACE+) (TEXT | SEMICOLON)+)*
  private static boolean just_text_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "just_text_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!just_text_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "just_text_1", c)) break;
    }
    return true;
  }

  // (SPACE+) (TEXT | SEMICOLON)+
  private static boolean just_text_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "just_text_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = just_text_1_0_0(b, l + 1);
    r = r && just_text_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // SPACE+
  private static boolean just_text_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "just_text_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SPACE);
    while (r) {
      int c = current_position_(b);
      if (!consumeToken(b, SPACE)) break;
      if (!empty_element_parsed_guard_(b, "just_text_1_0_0", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // (TEXT | SEMICOLON)+
  private static boolean just_text_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "just_text_1_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = just_text_1_0_1_0(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!just_text_1_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "just_text_1_0_1", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // TEXT | SEMICOLON
  private static boolean just_text_1_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "just_text_1_0_1_0")) return false;
    boolean r;
    r = consumeToken(b, TEXT);
    if (!r) r = consumeToken(b, SEMICOLON);
    return r;
  }

  /* ********************************************************** */
  // UNDERSCORE | varname parenthesized_disjoint? | parenthesized_disjoint
  public static boolean let_binding(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "let_binding")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LET_BINDING, "<let binding>");
    r = consumeToken(b, UNDERSCORE);
    if (!r) r = let_binding_1(b, l + 1);
    if (!r) r = parenthesized_disjoint(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // varname parenthesized_disjoint?
  private static boolean let_binding_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "let_binding_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = varname(b, l + 1);
    r = r && let_binding_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // parenthesized_disjoint?
  private static boolean let_binding_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "let_binding_1_1")) return false;
    parenthesized_disjoint(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // LET wspace let_binding (wspace EQ wspace expr wspace)?
  public static boolean let_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "let_stmt")) return false;
    if (!nextTokenIs(b, LET)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LET_STMT, null);
    r = consumeToken(b, LET);
    p = r; // pin = 1
    r = r && report_error_(b, wspace(b, l + 1));
    r = p && report_error_(b, let_binding(b, l + 1)) && r;
    r = p && let_stmt_3(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (wspace EQ wspace expr wspace)?
  private static boolean let_stmt_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "let_stmt_3")) return false;
    let_stmt_3_0(b, l + 1);
    return true;
  }

  // wspace EQ wspace expr wspace
  private static boolean let_stmt_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "let_stmt_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = wspace(b, l + 1);
    r = r && consumeToken(b, EQ);
    r = r && wspace(b, l + 1);
    r = r && expr(b, l + 1, -1);
    r = r && wspace(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // HEADING_MARKER | ENUM_MARKER | LIST_MARKER | TERM_MARKER
  static boolean marker(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "marker")) return false;
    boolean r;
    r = consumeToken(b, HEADING_MARKER);
    if (!r) r = consumeToken(b, ENUM_MARKER);
    if (!r) r = consumeToken(b, LIST_MARKER);
    if (!r) r = consumeToken(b, TERM_MARKER);
    return r;
  }

  /* ********************************************************** */
  // SPACE | LINEBREAK | ESCAPE | SHORTHAND | SMART_QUOTE | LINK | NEW_LINE | LABEL
  static boolean markup_expression_atom(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "markup_expression_atom")) return false;
    boolean r;
    r = consumeToken(b, SPACE);
    if (!r) r = consumeToken(b, LINEBREAK);
    if (!r) r = consumeToken(b, ESCAPE);
    if (!r) r = consumeToken(b, SHORTHAND);
    if (!r) r = consumeToken(b, SMART_QUOTE);
    if (!r) r = consumeToken(b, LINK);
    if (!r) r = consumeToken(b, NEW_LINE);
    if (!r) r = consumeToken(b, LABEL);
    return r;
  }

  /* ********************************************************** */
  // markup_expression_except_emph_strong | emph | strong
  static boolean markup_expression_disjoint(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "markup_expression_disjoint")) return false;
    boolean r;
    r = markup_expression_except_emph_strong(b, l + 1);
    if (!r) r = emph(b, l + 1);
    if (!r) r = strong(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // just_text | embedded_code | raw | markup_expression_atom | reference | equation
  static boolean markup_expression_except_emph_strong(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "markup_expression_except_emph_strong")) return false;
    boolean r;
    r = just_text(b, l + 1);
    if (!r) r = embedded_code(b, l + 1);
    if (!r) r = raw(b, l + 1);
    if (!r) r = markup_expression_atom(b, l + 1);
    if (!r) r = reference(b, l + 1);
    if (!r) r = equation(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // (paragraph | PARBREAK)*
  public static boolean markup_sequence(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "markup_sequence")) return false;
    Marker m = enter_section_(b, l, _NONE_, MARKUP_SEQUENCE, "<markup sequence>");
    while (true) {
      int c = current_position_(b);
      if (!markup_sequence_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "markup_sequence", c)) break;
    }
    exit_section_(b, l, m, true, false, null);
    return true;
  }

  // paragraph | PARBREAK
  private static boolean markup_sequence_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "markup_sequence_0")) return false;
    boolean r;
    r = paragraph(b, l + 1);
    if (!r) r = consumeToken(b, PARBREAK);
    return r;
  }

  /* ********************************************************** */
  // CODE_SPACE | TEXT | SLASH | UNDERSCORE | HAT | MATH_SHORTHAND | PRIME | MATH_ALIGN_POINT | ROOT | MATH_IDENT | STRING
  public static boolean math_expression_literal(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "math_expression_literal")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MATH_EXPRESSION_LITERAL, "<math expression literal>");
    r = consumeToken(b, CODE_SPACE);
    if (!r) r = consumeToken(b, TEXT);
    if (!r) r = consumeToken(b, SLASH);
    if (!r) r = consumeToken(b, UNDERSCORE);
    if (!r) r = consumeToken(b, HAT);
    if (!r) r = consumeToken(b, MATH_SHORTHAND);
    if (!r) r = consumeToken(b, PRIME);
    if (!r) r = consumeToken(b, MATH_ALIGN_POINT);
    if (!r) r = consumeToken(b, ROOT);
    if (!r) r = consumeToken(b, MATH_IDENT);
    if (!r) r = consumeToken(b, STRING);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // multiline_wspace (method_call_regular | method_call_content_only)
  public static boolean method_call(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_call")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, METHOD_CALL, "<method call>");
    r = multiline_wspace(b, l + 1);
    r = r && method_call_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // method_call_regular | method_call_content_only
  private static boolean method_call_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_call_1")) return false;
    boolean r;
    r = method_call_regular(b, l + 1);
    if (!r) r = method_call_content_only(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // DOT wspace IDENT content_block+
  public static boolean method_call_content_only(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_call_content_only")) return false;
    if (!nextTokenIs(b, DOT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DOT);
    r = r && wspace(b, l + 1);
    r = r && consumeToken(b, IDENT);
    r = r && method_call_content_only_3(b, l + 1);
    exit_section_(b, m, METHOD_CALL_CONTENT_ONLY, r);
    return r;
  }

  // content_block+
  private static boolean method_call_content_only_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_call_content_only_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = content_block(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!content_block(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "method_call_content_only_3", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // DOT wspace IDENT parenthesized_disjoint content_block*
  public static boolean method_call_regular(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_call_regular")) return false;
    if (!nextTokenIs(b, DOT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DOT);
    r = r && wspace(b, l + 1);
    r = r && consumeToken(b, IDENT);
    r = r && parenthesized_disjoint(b, l + 1);
    r = r && method_call_regular_4(b, l + 1);
    exit_section_(b, m, METHOD_CALL_REGULAR, r);
    return r;
  }

  // content_block*
  private static boolean method_call_regular_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "method_call_regular_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!content_block(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "method_call_regular_4", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // (CODE_SPACE | CODE_NEW_LINE_SPACE)*
  public static boolean multiline_wspace(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "multiline_wspace")) return false;
    Marker m = enter_section_(b, l, _NONE_, MULTILINE_WSPACE, "<multiline wspace>");
    while (true) {
      int c = current_position_(b);
      if (!multiline_wspace_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "multiline_wspace", c)) break;
    }
    exit_section_(b, l, m, true, false, null);
    return true;
  }

  // CODE_SPACE | CODE_NEW_LINE_SPACE
  private static boolean multiline_wspace_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "multiline_wspace_0")) return false;
    boolean r;
    r = consumeToken(b, CODE_SPACE);
    if (!r) r = consumeToken(b, CODE_NEW_LINE_SPACE);
    return r;
  }

  /* ********************************************************** */
  // marker markup_expression_disjoint* (COLON markup_expression_disjoint*)? | markup_expression_disjoint+
  public static boolean paragraph(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "paragraph")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PARAGRAPH, "<paragraph>");
    r = paragraph_0(b, l + 1);
    if (!r) r = paragraph_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // marker markup_expression_disjoint* (COLON markup_expression_disjoint*)?
  private static boolean paragraph_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "paragraph_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = marker(b, l + 1);
    r = r && paragraph_0_1(b, l + 1);
    r = r && paragraph_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // markup_expression_disjoint*
  private static boolean paragraph_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "paragraph_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!markup_expression_disjoint(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "paragraph_0_1", c)) break;
    }
    return true;
  }

  // (COLON markup_expression_disjoint*)?
  private static boolean paragraph_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "paragraph_0_2")) return false;
    paragraph_0_2_0(b, l + 1);
    return true;
  }

  // COLON markup_expression_disjoint*
  private static boolean paragraph_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "paragraph_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COLON);
    r = r && paragraph_0_2_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // markup_expression_disjoint*
  private static boolean paragraph_0_2_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "paragraph_0_2_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!markup_expression_disjoint(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "paragraph_0_2_0_1", c)) break;
    }
    return true;
  }

  // markup_expression_disjoint+
  private static boolean paragraph_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "paragraph_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = markup_expression_disjoint(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!markup_expression_disjoint(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "paragraph_1", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // empty_parens | empty_dict | parenthesized_thing
  static boolean parenthesized_disjoint(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parenthesized_disjoint")) return false;
    if (!nextTokenIs(b, LEFT_PAREN)) return false;
    boolean r;
    r = empty_parens(b, l + 1);
    if (!r) r = empty_dict(b, l + 1);
    if (!r) r = parenthesized_thing(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // wspace (COLON? DOTS? expr entry? | DOTS) wspace
  public static boolean parenthesized_element(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parenthesized_element")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PARENTHESIZED_ELEMENT, "<parenthesized element>");
    r = wspace(b, l + 1);
    r = r && parenthesized_element_1(b, l + 1);
    r = r && wspace(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // COLON? DOTS? expr entry? | DOTS
  private static boolean parenthesized_element_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parenthesized_element_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parenthesized_element_1_0(b, l + 1);
    if (!r) r = consumeToken(b, DOTS);
    exit_section_(b, m, null, r);
    return r;
  }

  // COLON? DOTS? expr entry?
  private static boolean parenthesized_element_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parenthesized_element_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parenthesized_element_1_0_0(b, l + 1);
    r = r && parenthesized_element_1_0_1(b, l + 1);
    r = r && expr(b, l + 1, -1);
    r = r && parenthesized_element_1_0_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // COLON?
  private static boolean parenthesized_element_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parenthesized_element_1_0_0")) return false;
    consumeToken(b, COLON);
    return true;
  }

  // DOTS?
  private static boolean parenthesized_element_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parenthesized_element_1_0_1")) return false;
    consumeToken(b, DOTS);
    return true;
  }

  // entry?
  private static boolean parenthesized_element_1_0_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parenthesized_element_1_0_3")) return false;
    entry(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // LEFT_PAREN parenthesized_element (COMMA parenthesized_element)* COMMA? wspace RIGHT_PAREN
  public static boolean parenthesized_thing(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parenthesized_thing")) return false;
    if (!nextTokenIs(b, LEFT_PAREN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, PARENTHESIZED_THING, null);
    r = consumeToken(b, LEFT_PAREN);
    p = r; // pin = 1
    r = r && report_error_(b, parenthesized_element(b, l + 1));
    r = p && report_error_(b, parenthesized_thing_2(b, l + 1)) && r;
    r = p && report_error_(b, parenthesized_thing_3(b, l + 1)) && r;
    r = p && report_error_(b, wspace(b, l + 1)) && r;
    r = p && consumeToken(b, RIGHT_PAREN) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (COMMA parenthesized_element)*
  private static boolean parenthesized_thing_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parenthesized_thing_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!parenthesized_thing_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "parenthesized_thing_2", c)) break;
    }
    return true;
  }

  // COMMA parenthesized_element
  private static boolean parenthesized_thing_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parenthesized_thing_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && parenthesized_element(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // COMMA?
  private static boolean parenthesized_thing_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parenthesized_thing_3")) return false;
    consumeToken(b, COMMA);
    return true;
  }

  /* ********************************************************** */
  // single_arg_closure | code_block | content_block | expr_with_parens | raw | equation | conditional |
  //     while_expr | return_expr | for_expr | identifier | primary_literal | stmt_disjoint
  static boolean primary_disjoint(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "primary_disjoint")) return false;
    boolean r;
    r = single_arg_closure(b, l + 1);
    if (!r) r = code_block(b, l + 1);
    if (!r) r = content_block(b, l + 1);
    if (!r) r = expr_with_parens(b, l + 1);
    if (!r) r = raw(b, l + 1);
    if (!r) r = equation(b, l + 1);
    if (!r) r = conditional(b, l + 1);
    if (!r) r = while_expr(b, l + 1);
    if (!r) r = return_expr(b, l + 1);
    if (!r) r = for_expr(b, l + 1);
    if (!r) r = identifier(b, l + 1);
    if (!r) r = primary_literal(b, l + 1);
    if (!r) r = stmt_disjoint(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // IDENT | BREAK | CONTINUE | NONE | AUTO | INT | FLOAT | NUMERIC | STRING | LABEL | BOOL
  public static boolean primary_literal(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "primary_literal")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PRIMARY_LITERAL, "<primary literal>");
    r = consumeToken(b, IDENT);
    if (!r) r = consumeToken(b, BREAK);
    if (!r) r = consumeToken(b, CONTINUE);
    if (!r) r = consumeToken(b, NONE);
    if (!r) r = consumeToken(b, AUTO);
    if (!r) r = consumeToken(b, INT);
    if (!r) r = consumeToken(b, FLOAT);
    if (!r) r = consumeToken(b, NUMERIC);
    if (!r) r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, LABEL);
    if (!r) r = consumeToken(b, BOOL);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // RAW_DELIM (RAW_CODE | BLOCKY_RAW_CODE)* RAW_DELIM
  public static boolean raw(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "raw")) return false;
    if (!nextTokenIs(b, RAW_DELIM)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, RAW, null);
    r = consumeToken(b, RAW_DELIM);
    p = r; // pin = 1
    r = r && report_error_(b, raw_1(b, l + 1));
    r = p && consumeToken(b, RAW_DELIM) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (RAW_CODE | BLOCKY_RAW_CODE)*
  private static boolean raw_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "raw_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!raw_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "raw_1", c)) break;
    }
    return true;
  }

  // RAW_CODE | BLOCKY_RAW_CODE
  private static boolean raw_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "raw_1_0")) return false;
    boolean r;
    r = consumeToken(b, RAW_CODE);
    if (!r) r = consumeToken(b, BLOCKY_RAW_CODE);
    return r;
  }

  /* ********************************************************** */
  // REF_MARKER content_block?
  public static boolean reference(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "reference")) return false;
    if (!nextTokenIs(b, REF_MARKER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, REF_MARKER);
    r = r && reference_1(b, l + 1);
    exit_section_(b, m, REFERENCE, r);
    return r;
  }

  // content_block?
  private static boolean reference_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "reference_1")) return false;
    content_block(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // chained_ident (wspace AS wspace IDENT)?
  public static boolean renamed_ident(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "renamed_ident")) return false;
    if (!nextTokenIs(b, IDENT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = chained_ident(b, l + 1);
    r = r && renamed_ident_1(b, l + 1);
    exit_section_(b, m, RENAMED_IDENT, r);
    return r;
  }

  // (wspace AS wspace IDENT)?
  private static boolean renamed_ident_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "renamed_ident_1")) return false;
    renamed_ident_1_0(b, l + 1);
    return true;
  }

  // wspace AS wspace IDENT
  private static boolean renamed_ident_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "renamed_ident_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = wspace(b, l + 1);
    r = r && consumeToken(b, AS);
    r = r && wspace(b, l + 1);
    r = r && consumeToken(b, IDENT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // RETURN wspace expr?
  public static boolean return_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "return_expr")) return false;
    if (!nextTokenIs(b, RETURN)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, RETURN_EXPR, null);
    r = consumeToken(b, RETURN);
    p = r; // pin = 1
    r = r && report_error_(b, wspace(b, l + 1));
    r = p && return_expr_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // expr?
  private static boolean return_expr_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "return_expr_2")) return false;
    expr(b, l + 1, -1);
    return true;
  }

  /* ********************************************************** */
  // markup_sequence
  static boolean root(PsiBuilder b, int l) {
    return markup_sequence(b, l + 1);
  }

  /* ********************************************************** */
  // SET wspace chained_ident wspace parenthesized_disjoint wspace (IF wspace expr)?
  public static boolean set_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "set_stmt")) return false;
    if (!nextTokenIs(b, SET)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SET_STMT, null);
    r = consumeToken(b, SET);
    p = r; // pin = 1
    r = r && report_error_(b, wspace(b, l + 1));
    r = p && report_error_(b, chained_ident(b, l + 1)) && r;
    r = p && report_error_(b, wspace(b, l + 1)) && r;
    r = p && report_error_(b, parenthesized_disjoint(b, l + 1)) && r;
    r = p && report_error_(b, wspace(b, l + 1)) && r;
    r = p && set_stmt_6(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (IF wspace expr)?
  private static boolean set_stmt_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "set_stmt_6")) return false;
    set_stmt_6_0(b, l + 1);
    return true;
  }

  // IF wspace expr
  private static boolean set_stmt_6_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "set_stmt_6_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IF);
    r = r && wspace(b, l + 1);
    r = r && expr(b, l + 1, -1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // SHOW wspace expr? wspace COLON wspace expr
  public static boolean show_stmt(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "show_stmt")) return false;
    if (!nextTokenIs(b, SHOW)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SHOW_STMT, null);
    r = consumeToken(b, SHOW);
    p = r; // pin = 1
    r = r && report_error_(b, wspace(b, l + 1));
    r = p && report_error_(b, show_stmt_2(b, l + 1)) && r;
    r = p && report_error_(b, wspace(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, COLON)) && r;
    r = p && report_error_(b, wspace(b, l + 1)) && r;
    r = p && expr(b, l + 1, -1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // expr?
  private static boolean show_stmt_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "show_stmt_2")) return false;
    expr(b, l + 1, -1);
    return true;
  }

  /* ********************************************************** */
  // (IDENT | UNDERSCORE) wspace ARROW wspace expr
  public static boolean single_arg_closure(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "single_arg_closure")) return false;
    if (!nextTokenIs(b, "<single arg closure>", IDENT, UNDERSCORE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SINGLE_ARG_CLOSURE, "<single arg closure>");
    r = single_arg_closure_0(b, l + 1);
    r = r && wspace(b, l + 1);
    r = r && consumeToken(b, ARROW);
    p = r; // pin = 3
    r = r && report_error_(b, wspace(b, l + 1));
    r = p && expr(b, l + 1, -1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // IDENT | UNDERSCORE
  private static boolean single_arg_closure_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "single_arg_closure_0")) return false;
    boolean r;
    r = consumeToken(b, IDENT);
    if (!r) r = consumeToken(b, UNDERSCORE);
    return r;
  }

  /* ********************************************************** */
  // IDENT | NONE | AUTO | BOOL | NOT | AND | OR | LET | SET | SHOW | CONTEXT | IF | ELSE | FOR | IN | WHILE | BREAK | CONTINUE | RETURN | IMPORT | INCLUDE | AS
  public static boolean soft_ident(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "soft_ident")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SOFT_IDENT, "<soft ident>");
    r = consumeToken(b, IDENT);
    if (!r) r = consumeToken(b, NONE);
    if (!r) r = consumeToken(b, AUTO);
    if (!r) r = consumeToken(b, BOOL);
    if (!r) r = consumeToken(b, NOT);
    if (!r) r = consumeToken(b, AND);
    if (!r) r = consumeToken(b, OR);
    if (!r) r = consumeToken(b, LET);
    if (!r) r = consumeToken(b, SET);
    if (!r) r = consumeToken(b, SHOW);
    if (!r) r = consumeToken(b, CONTEXT);
    if (!r) r = consumeToken(b, IF);
    if (!r) r = consumeToken(b, ELSE);
    if (!r) r = consumeToken(b, FOR);
    if (!r) r = consumeToken(b, IN);
    if (!r) r = consumeToken(b, WHILE);
    if (!r) r = consumeToken(b, BREAK);
    if (!r) r = consumeToken(b, CONTINUE);
    if (!r) r = consumeToken(b, RETURN);
    if (!r) r = consumeToken(b, IMPORT);
    if (!r) r = consumeToken(b, INCLUDE);
    if (!r) r = consumeToken(b, AS);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // let_stmt | show_stmt | set_stmt | import_stmt | include_stmt | context_expr
  static boolean stmt_disjoint(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "stmt_disjoint")) return false;
    boolean r;
    r = let_stmt(b, l + 1);
    if (!r) r = show_stmt(b, l + 1);
    if (!r) r = set_stmt(b, l + 1);
    if (!r) r = import_stmt(b, l + 1);
    if (!r) r = include_stmt(b, l + 1);
    if (!r) r = context_expr(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // STAR (markup_expression_except_emph_strong | emph)* STAR?
  public static boolean strong(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "strong")) return false;
    if (!nextTokenIs(b, STAR)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, STRONG, null);
    r = consumeToken(b, STAR);
    p = r; // pin = 1
    r = r && report_error_(b, strong_1(b, l + 1));
    r = p && strong_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (markup_expression_except_emph_strong | emph)*
  private static boolean strong_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "strong_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!strong_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "strong_1", c)) break;
    }
    return true;
  }

  // markup_expression_except_emph_strong | emph
  private static boolean strong_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "strong_1_0")) return false;
    boolean r;
    r = markup_expression_except_emph_strong(b, l + 1);
    if (!r) r = emph(b, l + 1);
    return r;
  }

  // STAR?
  private static boolean strong_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "strong_2")) return false;
    consumeToken(b, STAR);
    return true;
  }

  /* ********************************************************** */
  // IDENT
  public static boolean varname(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "varname")) return false;
    if (!nextTokenIs(b, IDENT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENT);
    exit_section_(b, m, VARNAME, r);
    return r;
  }

  /* ********************************************************** */
  // WHILE wspace expr wspace (code_block | content_block)
  public static boolean while_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "while_expr")) return false;
    if (!nextTokenIs(b, WHILE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, WHILE_EXPR, null);
    r = consumeToken(b, WHILE);
    p = r; // pin = 1
    r = r && report_error_(b, wspace(b, l + 1));
    r = p && report_error_(b, expr(b, l + 1, -1)) && r;
    r = p && report_error_(b, wspace(b, l + 1)) && r;
    r = p && while_expr_4(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // code_block | content_block
  private static boolean while_expr_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "while_expr_4")) return false;
    boolean r;
    r = code_block(b, l + 1);
    if (!r) r = content_block(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // (CODE_SPACE)*
  public static boolean wspace(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "wspace")) return false;
    Marker m = enter_section_(b, l, _NONE_, WSPACE, "<wspace>");
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, CODE_SPACE)) break;
      if (!empty_element_parsed_guard_(b, "wspace", c)) break;
    }
    exit_section_(b, l, m, true, false, null);
    return true;
  }

  /* ********************************************************** */
  // Expression root: expr
  // Operator priority table:
  // 0: BINARY(assign_expr) BINARY(add_assign_expr) BINARY(sub_assign_expr) BINARY(mul_assign_expr)
  //    BINARY(div_assign_expr)
  // 1: BINARY(or_expr)
  // 2: BINARY(and_expr)
  // 3: PREFIX(not_expr) BINARY(equals_expr) BINARY(unequals_expr) BINARY(less_expr)
  //    BINARY(less_or_eq_expr) BINARY(greater_expr) BINARY(greater_or_eq_expr) BINARY(in_expr)
  //    BINARY(not_in_expr)
  // 4: BINARY(add_expr) BINARY(sub_expr)
  // 5: BINARY(mul_expr) BINARY(div_expr)
  // 6: PREFIX(plus_expr) PREFIX(minus_expr)
  // 7: ATOM(atomic)
  public static boolean expr(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "expr")) return false;
    addVariant(b, "<expr>");
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<expr>");
    r = not_expr(b, l + 1);
    if (!r) r = plus_expr(b, l + 1);
    if (!r) r = minus_expr(b, l + 1);
    if (!r) r = atomic(b, l + 1);
    p = r;
    r = r && expr_0(b, l + 1, g);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  public static boolean expr_0(PsiBuilder b, int l, int g) {
    if (!recursion_guard_(b, l, "expr_0")) return false;
    boolean r = true;
    while (true) {
      Marker m = enter_section_(b, l, _LEFT_, null);
      if (g < 0 && assign_expr_0(b, l + 1)) {
        r = expr(b, l, -1);
        exit_section_(b, l, m, ASSIGN_EXPR, r, true, null);
      }
      else if (g < 0 && add_assign_expr_0(b, l + 1)) {
        r = expr(b, l, -1);
        exit_section_(b, l, m, ADD_ASSIGN_EXPR, r, true, null);
      }
      else if (g < 0 && sub_assign_expr_0(b, l + 1)) {
        r = expr(b, l, -1);
        exit_section_(b, l, m, SUB_ASSIGN_EXPR, r, true, null);
      }
      else if (g < 0 && mul_assign_expr_0(b, l + 1)) {
        r = expr(b, l, -1);
        exit_section_(b, l, m, MUL_ASSIGN_EXPR, r, true, null);
      }
      else if (g < 0 && div_assign_expr_0(b, l + 1)) {
        r = expr(b, l, -1);
        exit_section_(b, l, m, DIV_ASSIGN_EXPR, r, true, null);
      }
      else if (g < 1 && or_expr_0(b, l + 1)) {
        r = expr(b, l, 1);
        exit_section_(b, l, m, OR_EXPR, r, true, null);
      }
      else if (g < 2 && and_expr_0(b, l + 1)) {
        r = expr(b, l, 2);
        exit_section_(b, l, m, AND_EXPR, r, true, null);
      }
      else if (g < 3 && equals_expr_0(b, l + 1)) {
        r = expr(b, l, 3);
        exit_section_(b, l, m, EQUALS_EXPR, r, true, null);
      }
      else if (g < 3 && unequals_expr_0(b, l + 1)) {
        r = expr(b, l, 3);
        exit_section_(b, l, m, UNEQUALS_EXPR, r, true, null);
      }
      else if (g < 3 && less_expr_0(b, l + 1)) {
        r = expr(b, l, 3);
        exit_section_(b, l, m, LESS_EXPR, r, true, null);
      }
      else if (g < 3 && less_or_eq_expr_0(b, l + 1)) {
        r = expr(b, l, 3);
        exit_section_(b, l, m, LESS_OR_EQ_EXPR, r, true, null);
      }
      else if (g < 3 && greater_expr_0(b, l + 1)) {
        r = expr(b, l, 3);
        exit_section_(b, l, m, GREATER_EXPR, r, true, null);
      }
      else if (g < 3 && greater_or_eq_expr_0(b, l + 1)) {
        r = expr(b, l, 3);
        exit_section_(b, l, m, GREATER_OR_EQ_EXPR, r, true, null);
      }
      else if (g < 3 && in_expr_0(b, l + 1)) {
        r = expr(b, l, 3);
        exit_section_(b, l, m, IN_EXPR, r, true, null);
      }
      else if (g < 3 && not_in_expr_0(b, l + 1)) {
        r = expr(b, l, 3);
        exit_section_(b, l, m, NOT_IN_EXPR, r, true, null);
      }
      else if (g < 4 && add_expr_0(b, l + 1)) {
        r = expr(b, l, 4);
        exit_section_(b, l, m, ADD_EXPR, r, true, null);
      }
      else if (g < 4 && sub_expr_0(b, l + 1)) {
        r = expr(b, l, 4);
        exit_section_(b, l, m, SUB_EXPR, r, true, null);
      }
      else if (g < 5 && mul_expr_0(b, l + 1)) {
        r = expr(b, l, 5);
        exit_section_(b, l, m, MUL_EXPR, r, true, null);
      }
      else if (g < 5 && div_expr_0(b, l + 1)) {
        r = expr(b, l, 5);
        exit_section_(b, l, m, DIV_EXPR, r, true, null);
      }
      else {
        exit_section_(b, l, m, null, false, false, null);
        break;
      }
    }
    return r;
  }

  // wspace EQ wspace
  private static boolean assign_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "assign_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = wspace(b, l + 1);
    r = r && consumeToken(b, EQ);
    r = r && wspace(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // wspace PLUS_EQ wspace
  private static boolean add_assign_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_assign_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = wspace(b, l + 1);
    r = r && consumeToken(b, PLUS_EQ);
    r = r && wspace(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // wspace HYPH_EQ wspace
  private static boolean sub_assign_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sub_assign_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = wspace(b, l + 1);
    r = r && consumeToken(b, HYPH_EQ);
    r = r && wspace(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // wspace STAR_EQ wspace
  private static boolean mul_assign_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mul_assign_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = wspace(b, l + 1);
    r = r && consumeToken(b, STAR_EQ);
    r = r && wspace(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // wspace SLASH_EQ wspace
  private static boolean div_assign_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "div_assign_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = wspace(b, l + 1);
    r = r && consumeToken(b, SLASH_EQ);
    r = r && wspace(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // wspace OR wspace
  private static boolean or_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "or_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = wspace(b, l + 1);
    r = r && consumeToken(b, OR);
    r = r && wspace(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // wspace AND wspace
  private static boolean and_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "and_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = wspace(b, l + 1);
    r = r && consumeToken(b, AND);
    r = r && wspace(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  public static boolean not_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_expr")) return false;
    if (!nextTokenIsSmart(b, NOT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = not_expr_0(b, l + 1);
    p = r;
    r = p && expr(b, l, 3);
    exit_section_(b, l, m, NOT_EXPR, r, p, null);
    return r || p;
  }

  // NOT wspace
  private static boolean not_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, NOT);
    r = r && wspace(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // wspace EQ_EQ wspace
  private static boolean equals_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "equals_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = wspace(b, l + 1);
    r = r && consumeToken(b, EQ_EQ);
    r = r && wspace(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // wspace EXCL_EQ wspace
  private static boolean unequals_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unequals_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = wspace(b, l + 1);
    r = r && consumeToken(b, EXCL_EQ);
    r = r && wspace(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // wspace LT wspace
  private static boolean less_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "less_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = wspace(b, l + 1);
    r = r && consumeToken(b, LT);
    r = r && wspace(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // wspace LT_EQ wspace
  private static boolean less_or_eq_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "less_or_eq_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = wspace(b, l + 1);
    r = r && consumeToken(b, LT_EQ);
    r = r && wspace(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // wspace GT wspace
  private static boolean greater_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "greater_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = wspace(b, l + 1);
    r = r && consumeToken(b, GT);
    r = r && wspace(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // wspace GT_EQ wspace
  private static boolean greater_or_eq_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "greater_or_eq_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = wspace(b, l + 1);
    r = r && consumeToken(b, GT_EQ);
    r = r && wspace(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // wspace IN wspace
  private static boolean in_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "in_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = wspace(b, l + 1);
    r = r && consumeToken(b, IN);
    r = r && wspace(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // wspace NOT wspace IN wspace
  private static boolean not_in_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_in_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = wspace(b, l + 1);
    r = r && consumeToken(b, NOT);
    r = r && wspace(b, l + 1);
    r = r && consumeToken(b, IN);
    r = r && wspace(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // wspace PLUS wspace
  private static boolean add_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "add_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = wspace(b, l + 1);
    r = r && consumeToken(b, PLUS);
    r = r && wspace(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // wspace MINUS wspace
  private static boolean sub_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sub_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = wspace(b, l + 1);
    r = r && consumeToken(b, MINUS);
    r = r && wspace(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // wspace STAR wspace
  private static boolean mul_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mul_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = wspace(b, l + 1);
    r = r && consumeToken(b, STAR);
    r = r && wspace(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // wspace SLASH wspace
  private static boolean div_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "div_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = wspace(b, l + 1);
    r = r && consumeToken(b, SLASH);
    r = r && wspace(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  public static boolean plus_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "plus_expr")) return false;
    if (!nextTokenIsSmart(b, PLUS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = plus_expr_0(b, l + 1);
    p = r;
    r = p && expr(b, l, 6);
    exit_section_(b, l, m, PLUS_EXPR, r, p, null);
    return r || p;
  }

  // PLUS wspace
  private static boolean plus_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "plus_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, PLUS);
    r = r && wspace(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  public static boolean minus_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "minus_expr")) return false;
    if (!nextTokenIsSmart(b, MINUS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = minus_expr_0(b, l + 1);
    p = r;
    r = p && expr(b, l, 6);
    exit_section_(b, l, m, MINUS_EXPR, r, p, null);
    return r || p;
  }

  // MINUS wspace
  private static boolean minus_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "minus_expr_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokenSmart(b, MINUS);
    r = r && wspace(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // primary_disjoint (method_call | field_access | function_call)*
  public static boolean atomic(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "atomic")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, ATOMIC, "<atomic>");
    r = primary_disjoint(b, l + 1);
    p = r; // pin = 1
    r = r && atomic_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // (method_call | field_access | function_call)*
  private static boolean atomic_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "atomic_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!atomic_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "atomic_1", c)) break;
    }
    return true;
  }

  // method_call | field_access | function_call
  private static boolean atomic_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "atomic_1_0")) return false;
    boolean r;
    r = method_call(b, l + 1);
    if (!r) r = field_access(b, l + 1);
    if (!r) r = function_call(b, l + 1);
    return r;
  }

}
