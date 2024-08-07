// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package org.ldemetrios.kvasir.syntax;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;
import java.util.ArrayList;
import static org.ldemetrios.kvasir.syntax.ErroneousTypes.*;
import static org.ldemetrios.kvasir.syntax.TypstTypes.*;

%%

%class TypstLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

%{
    ArrayList<Integer> stack = new ArrayList<>();
    boolean newline = true;
    boolean was_word = false;
    boolean eofHandled = false;

    void end_token() {
        eofHandled = false;
        CharSequence str = yytext();
        char last = str.charAt(str.length() - 1);
        was_word = Character.isAlphabetic(last) || Character.isDigit(last); // TODO Handle scripts
        newline = false;
        for (int i = str.length() - 1;  i >= 0; i--) {
            newline = "\n\r\u2028\u2029\u0085\u000B\u000C".contains( ""  + str.charAt(i));
            if(newline) break;
            if (!Character.isWhitespace(str.charAt(i))) break;
        }
    }

    void push(int newState) {
        stack.add(yystate());
        yybegin(newState);
    }

    int pop() {
        int state = stack.remove(stack.size() - 1);
        yybegin(state);
        return state;
    }

    IElementType onEof(IElementType token) {
        if (eofHandled) {
            eofHandled = false;
            return null;
        }
        eofHandled = true;
        clear();
        return token;
    }

    void clear() {
        newline = false;
        was_word = false;
        stack.clear();
    }

    IElementType possiblyKeyword(CharSequence text) {
        return switch(text.toString()) {
            case "none" -> NONE;
            case "auto" -> AUTO;
            case "true" -> BOOL;
            case "false" -> BOOL;
            case "not" -> NOT;
            case "and" -> AND;
            case "or" -> OR;
            case "let" -> LET;
            case "set" -> SET;
            case "show" -> SHOW;
            case "context" -> CONTEXT;
            case "if" -> IF;
            case "else" -> ELSE;
            case "for" -> FOR;
            case "in" -> IN;
            case "while" -> WHILE;
            case "break" -> BREAK;
            case "continue" -> CONTINUE;
            case "return" -> RETURN;
            case "import" -> IMPORT;
            case "include" -> INCLUDE;
            case "as" -> AS;
            default -> IDENT;
        };
    }

    int whiteToken() {
        end_token();
        String token = yytext().toString().replace("\r\n", "x");
        int newlines = 0;
        for (char c : token.toCharArray()) {
         if ("\n\r\u2028\u2029\u0085\u000B\u000C".contains( ""  + c)) newlines++;
        }
        newline = newlines > 0;
        was_word = false;
        return newlines;
    }

    int popMode(int mode) {
        if (yystate() == mode) {
            return pop();
        }
        while(!stack.isEmpty()) {
            if(pop() == mode) {
                break;
            }
        }
        if(!stack.isEmpty())pop();
        return yystate();
    }

    int were_backticks = 0;
%}

%state MARKUP RAW BLOCK_COMMENT_MODE BLOCKY_RAW MATH

%state EMBEDDED_CODE_EXPR NON_ATOMIC_EXPR IF_ELSE_EXPR WHILE_EXPR EMBEDDED_STATEMENT CODE_BLOCK PARENTHESIZED
// Crutch states:
%state UNICODE_ESCAPE LINK_MODE LINK_PAR LINK_SQ LINK_CURLY LINK_ANGLE LABEL_MODE AFTER_REF TERM
%state FOR_EXPR FOR_EXPR_AFTER_PATTERN FOR_EXPR_AFTER_IN
%state IF_ELSE_EXPR, AFTER_BRANCH

// Classes
NEWLINE=(\r\n|[\n\r\u2028\u2029\u0085\u000B\u000C])
SPACE=(" " | \t) // ([[:space:]-[\n\r\u2028\u2029\u0085\u000B\u000C]])
WHITE=(\s | {NEWLINE})

ID_START=(\p{XID_START} | "_")
ID_CONTINUE=(\p{XID_CONTINUE} | "_" | "-")
NOT_ID_CONTINUE=[^\p{XID_CONTINUE}_-]

MATH_ID_START=\p{XID_START}
MATH_ID_CONTINUE=[\p{XID_CONTINUE}_-] // At least I hope that'll work TODO
VALID_IN_LABEL_LITERAL={ID_CONTINUE} | ":" | "."
VALID_END_LABEL_LITERAL={ID_CONTINUE}

ASCII_DIGIT=[0-9]
ASCII_ALPHANUM=[0-9A-Za-z]
ASCII_HEX=[0-9A-Fa-f]


// Markup stuff

MARKUP_SHORTHANDS="..." | "---" | "--" | "-?" | "~"
IN_LINK_CHAR=[0-9a-zA-Z!#$%&*+,\-./:;=?@_!']
NOT_IN_LINK_CHAR=[^0-9a-zA-Z!#$%&*+,\-./:;=?@_!'\)\]\}]

// Code stuff

//CONTINUE_ATOMIC_CODE_IF=[\(\[] | ({WHITE})*\.({ID_START})
CONTINUE_ATOMIC_CODE_IF=[\(\[] | \.({ID_START})
CONTINUE_NON_ATOMIC_IF={CONTINUE_ATOMIC_CODE_IF} | {SPACE}* ( == | \+ | \$ | \* | \/ | - | \u2212 | "<" | ">" | "!=" | ((and|or|not|in) {NOT_ID_CONTINUE}) | "=>")

IDENT={ID_START}({ID_CONTINUE})*

FLOAT=([0-9]*\.[0-9]+) ([eE][+-]?[0-9]+)? | [0-9]+([eE][+-]?[0-9]+)
INT=[0-9]+|0[box]([A-Fa-f0-9]+)
NUMERIC_FLOAT=({FLOAT})(%|[a-zA-Z]+)
NUMERIC_INT=({INT})(%|[a-zA-Z]+)

STRING= \"([^\\\"]|\\.)*\"


// Math stuff

MATH_SHORTHANDS="->>" | "->" | "-->" | ":=" | "::=" | "!=" | "..." |
    "[|" | "<==>" | "<-->" | "<--" | "<-<" | "<->" | "<<-" | "<<<" |
    "<=>" | "<==" | "<~~" | "<=" | "<<" | "<-" | "<~" | ">->" | ">>>" |
    "==>" | "=>" | "=:" | ">=" | ">>" | "|->" | "|=>" | "|]" | "||" | "~~>" | "~>" | "-" | "*"

%%

<EMBEDDED_CODE_EXPR, CODE_BLOCK> {
    {SPACE}+                                                            { whiteToken(); return CODE_SPACE; }
    {NEWLINE}                                                           { whiteToken(); return CODE_NEW_LINE_SPACE; }
}

<PARENTHESIZED, MATH> {
    {WHITE}+                                                            { whiteToken(); return CODE_SPACE; }
}

<FOR_EXPR, FOR_EXPR_AFTER_PATTERN, FOR_EXPR_AFTER_IN,
    NON_ATOMIC_EXPR, IF_ELSE_EXPR, AFTER_BRANCH> {
    {WHITE}+                                                            {
                                                                            int newlines = whiteToken();
                                                                            if (newlines >= 1) {
                                                                                int was = pop();
                                                                                if (was == FOR_EXPR_AFTER_IN || was == IF_ELSE_EXPR || was == WHILE_EXPR) pop();
                                                                                pop();
                                                                            }
                                                                            return newlines >= 1 ? CODE_NEW_LINE_SPACE : CODE_SPACE;
                                                                        }
}

<EMBEDDED_STATEMENT, WHILE_EXPR> {
    {WHITE}+                                                            {
                                                                            int newlines = whiteToken();
                                                                            if (newlines >= 1) {
                                                                                if (pop() == EMBEDDED_CODE_EXPR) pop();
                                                                            }
                                                                            return newlines >= 2 ? PARBREAK : newlines == 1? NEW_LINE : CODE_SPACE;
                                                                        }
}

<YYINITIAL, MARKUP, TERM> {
    {NEWLINE} ({SPACE}* {NEWLINE})+                                     { whiteToken(); return PARBREAK; }
}


<YYINITIAL, MARKUP, TERM> {
        "\\u{"                                                          { push(UNICODE_ESCAPE); }
        \\{WHITE}                                                       { end_token(); return LINEBREAK; }
        \\.                                                             { end_token(); return ESCAPE; }

        "http://" / {IN_LINK_CHAR} | [\[\(\{\<]                         { push(LINK_MODE); }
        "https://" / {IN_LINK_CHAR} | [\[\(\{\<]                        { push(LINK_MODE); }
        "http://"                                                       { return EMPTY_LINK; }
        "https://"                                                      { return EMPTY_LINK; }
}



<YYINITIAL, MARKUP, MATH, EMBEDDED_CODE_EXPR, NON_ATOMIC_EXPR,
    IF_ELSE_EXPR, WHILE_EXPR, FOR_EXPR, EMBEDDED_STATEMENT,
    CODE_BLOCK, PARENTHESIZED> {
    "//" [^\n\r\u2028\u2029\u0085\u000B\u000C]*                         { end_token(); return LINE_COMMENT; }
    "/*"                                                                { push(BLOCK_COMMENT_MODE); }
    "*/"                                                                { end_token(); return UNEXPECTED_BLOCK_COMMENT; }
}

<BLOCK_COMMENT_MODE> {
    "/*"                                                                { push(BLOCK_COMMENT_MODE); }
    "*/"                                                                { if (pop() != BLOCK_COMMENT_MODE) return BLOCK_COMMENT; }
    [^]                                                                 { }
    <<EOF>>                                                             { return onEof(BLOCK_COMMENT); }
}

<YYINITIAL, MARKUP, TERM> {
        {SPACE}* "="+ / {WHITE}                                         { IElementType token = newline ? HEADING_MARKER : TEXT; end_token(); return token; }

        {SPACE}* "-" / {WHITE}                                          { IElementType token = newline ? LIST_MARKER : TEXT; end_token(); return token; }
        {SPACE}* "+" / {WHITE}                                          { IElementType token = newline ? ENUM_MARKER : TEXT; end_token(); return token; }
        {SPACE}* "/" / {WHITE}                                          {
                                                                            IElementType token = newline ? TERM_MARKER : TEXT;
                                                                            if(newline) push(TERM);
                                                                            end_token();
                                                                            return token;
                                                                        }
        {SPACE}* [0-9]+\. / {WHITE}                                     {  IElementType token = newline ? ENUM_MARKER : TEXT; end_token(); return token; }
}

<YYINITIAL, MARKUP, TERM> {
        {SPACE}+                                                        { whiteToken(); return SPACE; }
        {NEWLINE}                                                       { whiteToken(); return SPACE; }
}


<YYINITIAL, MARKUP, TERM> {
        "<" / {ID_CONTINUE}                                             { push(LABEL_MODE); }
        "@" {VALID_IN_LABEL_LITERAL}* {VALID_END_LABEL_LITERAL} / "["   { newline=false; was_word = false; push(AFTER_REF); return REF_MARKER; }
        "@" {VALID_IN_LABEL_LITERAL}* {VALID_END_LABEL_LITERAL}         { newline=false; was_word = false; return REF_MARKER; }
        "@" / "["                                                       { newline=false; was_word = false; push(AFTER_REF); return REF_MARKER; }
        "@"                                                             { newline=false; was_word = false; return REF_MARKER; }

        {MARKUP_SHORTHANDS}                                             { newline=false; was_word=false; return SHORTHAND; }
        "-" / \d                                                        { newline=false; was_word=false; return SHORTHAND; }

        "_" / [^[:alpha:][:digit:]]                                     { end_token(); return UNDERSCORE; }
        "_"                                                             { IElementType token = was_word ? TEXT : UNDERSCORE; end_token(); return token; }
        "*" / [^[:alpha:][:digit:]]                                     { end_token(); return STAR; }
        "*"                                                             { IElementType token = was_word ? TEXT : STAR; end_token(); return token; }

        "#"                                                             { end_token(); push(EMBEDDED_CODE_EXPR); return HASH; }
        "'"                                                             { end_token(); return SMART_QUOTE; }
        \"                                                              { end_token(); return SMART_QUOTE; }
        "$"                                                             { end_token(); push(MATH); return DOLLAR; }
        ";"                                                             { end_token(); return SEMICOLON; /* In support for embedded exprs */ }
}

<YYINITIAL, MARKUP> {
        "["                                                             { push(MARKUP); end_token(); return TEXT; }
        ":"                                                             { end_token(); return TEXT; }
}

<TERM> {
        "["                                                             { end_token(); push(MARKUP); return TEXT; }
        ":"                                                             { end_token(); pop(); return COLON; }
        "]"                                                             { pop(); yypushback(1); }
}


<AFTER_REF> {
    "["                                                                 { end_token(); push(MARKUP); return LEFT_BRACKET; }
    [^]                                                                 { throw new AssertionError(); }
}

<MARKUP> {
        "]" / {CONTINUE_ATOMIC_CODE_IF}                                 {
                                                                            int now = pop();
                                                                            end_token();
                                                                            // Don't
                                                                            if (now == AFTER_REF) pop();
                                                                            return now == MARKUP || now == YYINITIAL || now == TERM ? TEXT : RIGHT_BRACKET;
                                                                        }
        "]" / {CONTINUE_NON_ATOMIC_IF}                                  {
                                                                            int now = pop();
                                                                            end_token();
                                                                            if (now == EMBEDDED_CODE_EXPR || now == AFTER_REF) pop();
                                                                            // Don't
                                                                            return now == MARKUP || now == YYINITIAL || now == TERM ? TEXT : RIGHT_BRACKET;
                                                                        }
        "]" / {WHITE}* else                                             {
                                                                            int now = pop();
                                                                            if (now == AFTER_REF) pop();

                                                                            return now == MARKUP || now == YYINITIAL || now == TERM ? TEXT : RIGHT_BRACKET;
                                                                        }
        "]"                                                             {
                                                                            int now = pop();
                                                                            end_token();
                                                                            if (now == AFTER_BRANCH) {
                                                                                pop(); pop();
                                                                                return RIGHT_BRACKET;
                                                                            }
                                                                            if (now == EMBEDDED_CODE_EXPR || now == NON_ATOMIC_EXPR || now == AFTER_REF) pop();
                                                                            return now == MARKUP || now == YYINITIAL || now == TERM ? TEXT : RIGHT_BRACKET;
                                                                        }
}

<YYINITIAL> {
    "]"                                                                 { return TEXT; }
}

<EMBEDDED_STATEMENT, YYINITIAL, MARKUP, EMBEDDED_CODE_EXPR,
    CODE_BLOCK, PARENTHESIZED, NON_ATOMIC_EXPR, TERM> {
    "`" / "`" [^\`]                                                     { push(RAW); return RAW_DELIM; }
    "`" / [^\`]                                                         { push(RAW); return RAW_DELIM; }
    "`"+                                                                {
                                                                            were_backticks = yytext().length();
                                                                            if(were_backticks >= 3) {
                                                                                push(BLOCKY_RAW);
                                                                                return RAW_DELIM;
                                                                            } else {
                                                                                push(RAW);
                                                                                return RAW_DELIM;
                                                                            }
                                                                        }
}

<YYINITIAL, MARKUP, TERM> {
    [^\n\r\u2028\u2029\u0085\u000B\u000C\sh/\*=\-+0-9<@_*#'\"$;\[\]:`\.~]+ { end_token(); return TEXT; }
    [^]                                                                    { end_token(); return TEXT; }
}
<UNICODE_ESCAPE> {
      {ASCII_ALPHANUM}+                                                 { }
      "}"                                                               { pop(); end_token(); return ESCAPE; }
      [^]                                                               { pop(); end_token(); return UNCLOSED_UNICODE_ESCAPE; }
      <<EOF>>                                                           { return onEof(UNCLOSED_UNICODE_ESCAPE); }
}

<LABEL_MODE> {
    {VALID_IN_LABEL_LITERAL}+                                           { }
    ">" / {CONTINUE_ATOMIC_CODE_IF}                                     { return LABEL; }
    ">"                                                                 { if(pop() == EMBEDDED_CODE_EXPR) pop(); end_token(); return LABEL; }
    [^]                                                                 { pop(); return UNCLOSED_LABEL; }
    <<EOF>>                                                             { return onEof(UNCLOSED_LABEL); }
}

<LINK_MODE, LINK_PAR, LINK_SQ, LINK_CURLY, LINK_ANGLE> {
    "("                                                                 { push(LINK_PAR); }
    "["                                                                 { push(LINK_SQ); }
    "{"                                                                 { push(LINK_CURLY); }
    "<"                                                                 { push(LINK_ANGLE); }
}
<LINK_PAR, LINK_SQ, LINK_CURLY, LINK_ANGLE> {
    <<EOF>>                                                             { return onEof(UNBALANCED_LINK); }
}
<LINK_MODE> {
    {IN_LINK_CHAR} / [\)\]\}\>]                                         { newline=false; was_word = false; pop(); return LINK; }
    [\)\]\}\>]                                                          { newline=false; was_word = false; pop(); return UNREACHABLE; }
    {IN_LINK_CHAR} / (\.)* {NOT_IN_LINK_CHAR}                           { newline=false; was_word = false; pop(); return LINK; }
    <<EOF>>                                                             { return onEof(LINK); }
    [^]                                                                 {  }
}

<LINK_PAR> {
    [\]\}\>]                                                            { newline=false; was_word = false; pop(); return UNBALANCED_LINK; }
    {NOT_IN_LINK_CHAR}                                                  { newline=false; was_word = false; pop(); return UNBALANCED_LINK; }
    ")"                                                                 { pop(); }
    [^]                                                                 {  }
 }
<LINK_SQ>  {
    [\)\}\>]                                                            { newline=false; was_word = false; pop(); return UNBALANCED_LINK; }
    {NOT_IN_LINK_CHAR}                                                  { newline=false; was_word = false; pop(); return UNBALANCED_LINK; }
    "]"                                                                 { pop(); }
    [^]                                                                 {  }
 }
<LINK_CURLY>  {
    [\)\]\>]                                                            { newline=false; was_word = false; pop(); return UNBALANCED_LINK; }
    {NOT_IN_LINK_CHAR}                                                  { newline=false; was_word = false; pop(); return UNBALANCED_LINK; }
    "}"                                                                 { pop(); }
    [^]                                                                 {  }
 }
<LINK_ANGLE>  {
    [\)\]\}]                                                            { newline=false; was_word = false; pop(); return UNBALANCED_LINK; }
    {NOT_IN_LINK_CHAR}                                                  { newline=false; was_word = false; pop(); return UNBALANCED_LINK; }
    ">"                                                                 { pop(); }
    [^]                                                                 {  }
}

<RAW> {
    [^`]+                                                               { return RAW_CODE; }
    `                                                                   {
                                                                            newline=false; was_word=false;
                                                                            if (pop() == EMBEDDED_CODE_EXPR) pop();
                                                                            return RAW_DELIM;
                                                                        }
    <<EOF>>                                                             { return onEof(UNCLOSED_RAW); }
}

<BLOCKY_RAW> {
    ([^`]|`[^`]|``[^`])+                                                                                                                                                                             { return BLOCKY_RAW_CODE; }
    ```+                                                                {
                                                                             int found = yytext().length();
                                                                             if (found < were_backticks) {
                                                                                 return BLOCKY_RAW_CODE;
                                                                             } else {
                                                                                 yypushback(found - were_backticks);
                                                                                 pop();
                                                                                 return RAW_DELIM;
                                                                             }
                                                                        }
    <<EOF>>                                                             { return onEof(UNCLOSED_RAW); }
}

<EMBEDDED_CODE_EXPR> {
    if / {NOT_ID_CONTINUE}                                              { push(IF_ELSE_EXPR); push(NON_ATOMIC_EXPR); return IF; }
    (let | set | show | import | include) / {NOT_ID_CONTINUE}           { pop(); push(EMBEDDED_STATEMENT); return possiblyKeyword(yytext()); }
    context                                                             { return CONTEXT; }
    while / {NOT_ID_CONTINUE}                                           { push(WHILE_EXPR); push(NON_ATOMIC_EXPR); return WHILE; }
    for / {NOT_ID_CONTINUE}                                             { push(FOR_EXPR); return FOR; }

    none / {CONTINUE_ATOMIC_CODE_IF}                                    { return NONE; }
    auto / {CONTINUE_ATOMIC_CODE_IF}                                    { return AUTO; }
    {NUMERIC_FLOAT}  / {CONTINUE_ATOMIC_CODE_IF}                        { return NUMERIC; }
    {FLOAT} / {CONTINUE_ATOMIC_CODE_IF}                                 { return FLOAT; }
    {NUMERIC_INT}  / {CONTINUE_ATOMIC_CODE_IF}                          { return NUMERIC; }
    {INT} / {CONTINUE_ATOMIC_CODE_IF}                                   { return INT; }
    true | false / {CONTINUE_ATOMIC_CODE_IF}                            { return BOOL; }
    "<" / {ID_CONTINUE}                                                 { push(LABEL_MODE); }

    {STRING}                                                            { end_token(); pop(); return STRING; }
    none                                                                { end_token(); pop(); return NONE; }
    auto                                                                { end_token(); pop(); return AUTO; }
    {NUMERIC_FLOAT}                                                     { end_token(); pop(); return NUMERIC; }
    {FLOAT}                                                             { end_token(); pop(); return FLOAT; }
    {NUMERIC_INT}                                                       { end_token(); pop(); return NUMERIC; }
    {INT}                                                               { end_token(); pop(); return INT; }
    true | false                                                        { end_token(); pop(); return BOOL; }
    {STRING}                                                            { end_token(); pop(); return STRING; }

    "."                                                                 { end_token(); return DOT; }

    {IDENT} / {CONTINUE_ATOMIC_CODE_IF}                                 { end_token(); return IDENT; }
    {IDENT}                                                             { end_token(); pop(); return IDENT;}
    "["                                                                 { end_token(); push(MARKUP); return LEFT_BRACKET; }
    "{"                                                                 { push(CODE_BLOCK); return LEFT_BRACE; }
    "("                                                                 { push(PARENTHESIZED); return LEFT_PAREN; }

    "$"                                                                 { push(MATH); return DOLLAR;}
}

<NON_ATOMIC_EXPR> {
    "<" / {VALID_IN_LABEL_LITERAL}                                      { push(LABEL_MODE); }
    {NUMERIC_FLOAT} / {CONTINUE_NON_ATOMIC_IF}                          { /* Just continue parsing non atomic */ return NUMERIC; }
    {FLOAT} / {CONTINUE_NON_ATOMIC_IF}                                  { /* Just continue parsing non atomic */ return FLOAT; }
    {NUMERIC_INT} / {CONTINUE_NON_ATOMIC_IF}                            { /* Just continue parsing non atomic */ return NUMERIC; }
    {INT} / {CONTINUE_NON_ATOMIC_IF}                                    { /* Just continue parsing non atomic */ return INT; }
    {STRING} / {CONTINUE_NON_ATOMIC_IF}                                 { /* Just continue parsing non atomic */ return STRING; }
    {NUMERIC_FLOAT}                                                     { int was = pop(); /*Agh, some checks? pop(); */ return NUMERIC; }
    {FLOAT}                                                             { int was = pop(); /*Agh, some checks? pop(); */ return FLOAT; }
    {NUMERIC_INT}                                                       { int was = pop(); /*Agh, some checks? pop(); */ return NUMERIC; }
    {INT}                                                               { int was = pop(); /*Agh, some checks? pop(); */ return INT; }
    {STRING}                                                            { int was = pop(); /*Agh, some checks? pop(); */ return STRING; }
    "{"                                                                 { push(CODE_BLOCK); return LEFT_BRACE; }
    "["                                                                 { push(MARKUP); return LEFT_BRACKET; }
    "("                                                                 { push(PARENTHESIZED); return LEFT_PAREN; }

    "=="                                                                { return EQ_EQ; }
    "!="                                                                { return EXCL_EQ; }
    "<="                                                                { return LT_EQ; }
    ">="                                                                { return GT_EQ; }
    "+="                                                                { return PLUS_EQ; }
    ("-" | \u2212 ) "="                                                 { return HYPH_EQ; }
    "*="                                                                { return STAR_EQ; }
    "/="                                                                { return SLASH_EQ; }
    ".."                                                                { return DOTS; }
    "=>"                                                                { return ARROW; }
    "$"                                                                 { push(MATH); return DOLLAR; }
    ","                                                                 { return COMMA; }
    ";"                                                                 { return SEMICOLON; }
    ":"                                                                 { return COLON; }
    "."                                                                 { return DOT; }
    "+"                                                                 { return PLUS; }
    "-" | \u2212                                                        { return MINUS; }
    "*"                                                                 { return STAR; }
    "/"                                                                 { return SLASH; }
    "="                                                                 { return EQ; }
    "<"                                                                 { return LT; }
    ">"                                                                 { return GT; }
    "_" / {NOT_ID_CONTINUE}                                             { return UNDERSCORE; }
    not                                                                 { return NOT; }
    and                                                                 { return AND; }
    or                                                                  { return OR; }
    in                                                                  { return IN; }
    {ID_START} {ID_CONTINUE}* / {CONTINUE_NON_ATOMIC_IF}                { return possiblyKeyword(yytext()); }
    {ID_START} {ID_CONTINUE}*                                           { pop(); return possiblyKeyword(yytext()); }
}

<FOR_EXPR> {
    "("                                                                 { pop(); push(FOR_EXPR_AFTER_PATTERN); push(PARENTHESIZED); return LEFT_PAREN; }
    "_" / {NOT_ID_CONTINUE}                                             { pop(); push(FOR_EXPR_AFTER_PATTERN); return UNDERSCORE; }
    {ID_START} {ID_CONTINUE}*                                           { pop(); push(FOR_EXPR_AFTER_PATTERN); return possiblyKeyword(yytext()); }
}

<FOR_EXPR_AFTER_PATTERN> {
    {ID_START} {ID_CONTINUE}*                                           {
                                                                             IElementType it = possiblyKeyword(yytext());
                                                                             pop();
                                                                             if (it == IN) {
                                                                                 push(FOR_EXPR_AFTER_IN);
                                                                                 push(NON_ATOMIC_EXPR);
                                                                             }
                                                                             return it;
                                                                        }
}

<FOR_EXPR_AFTER_IN> {
    "["                                                                 { pop(); push(MARKUP); return LEFT_BRACKET; }
    "{"                                                                 { pop(); push(CODE_BLOCK); return LEFT_BRACE; }
}


<IF_ELSE_EXPR> {
    "["                                                                 { pop(); push(AFTER_BRANCH); push(MARKUP); return LEFT_BRACKET; }
    "{"                                                                 { pop(); push(AFTER_BRANCH); push(CODE_BLOCK); return LEFT_BRACE; }
}

<AFTER_BRANCH> {
    else                                                                { pop(); /*We're now in embedded code expr, so just continue parsing {}, [] or if-else*/ return ELSE;}
}

<WHILE_EXPR> {
    "["                                                                 { pop(); push(MARKUP); return LEFT_BRACKET; }
    "{"                                                                 { pop(); push(CODE_BLOCK); return LEFT_BRACE; }
}

<FOR_EXPR, FOR_EXPR_AFTER_PATTERN, FOR_EXPR_AFTER_IN, WHILE_EXPR> {
    [^]                                                                 { pop(); return TokenType.BAD_CHARACTER; }
}



<EMBEDDED_STATEMENT> {
    ";"                                                                 { pop(); return SEMICOLON; }
}

<EMBEDDED_STATEMENT, CODE_BLOCK, PARENTHESIZED> {
    "<" / {VALID_IN_LABEL_LITERAL}                                      { push(LABEL_MODE); }
    {NUMERIC_FLOAT}                                                     { return NUMERIC; }
    {FLOAT}                                                             { return FLOAT; }
    {NUMERIC_INT}                                                       { return NUMERIC; }
    {INT}                                                               { return INT; }
    {STRING}                                                            { return STRING; }
    "=="                                                                { return EQ_EQ; }
    "!="                                                                { return EXCL_EQ; }
    "<="                                                                { return LT_EQ; }
    ">="                                                                { return GT_EQ; }
    "+="                                                                { return PLUS_EQ; }
    ("-" | \u2212 ) "="                                                 { return HYPH_EQ; }
    "*="                                                                { return STAR_EQ; }
    "/="                                                                { return SLASH_EQ; }
    ".."                                                                { return DOTS; }
    "=>"                                                                { return ARROW; }
    "{"                                                                 { push(CODE_BLOCK); return LEFT_BRACE; }
    "["                                                                 { push(MARKUP); return LEFT_BRACKET; }
    "("                                                                 { push(PARENTHESIZED); return LEFT_PAREN; }
    "$"                                                                 { push(MATH); return DOLLAR; }
    ","                                                                 { return COMMA; }
    ";"                                                                 { return SEMICOLON; }
    ":"                                                                 { return COLON; }
    "."                                                                 { return DOT; }
    "+"                                                                 { return PLUS; }
    "-" | \u2212                                                        { return MINUS; }
    "*"                                                                 { return STAR; }
    "/"                                                                 { return SLASH; }
    "="                                                                 { return EQ; }
    "<"                                                                 { return LT; }
    ">"                                                                 { return GT; }
    "_" / {NOT_ID_CONTINUE}                                             { return UNDERSCORE; }
    {ID_START} {ID_CONTINUE}*                                           { return possiblyKeyword(yytext()); }
}

<CODE_BLOCK, PARENTHESIZED, AFTER_BRANCH, EMBEDDED_STATEMENT> {
    ")" / {CONTINUE_ATOMIC_CODE_IF}                                     { popMode(PARENTHESIZED); return RIGHT_PAREN; }
    ")" / {CONTINUE_NON_ATOMIC_IF}                                      { if(popMode(PARENTHESIZED) == EMBEDDED_CODE_EXPR) pop(); return RIGHT_PAREN; }
    ")"                                                                 {
                                                                            int was = popMode(PARENTHESIZED);
                                                                            if(was == EMBEDDED_CODE_EXPR || was == NON_ATOMIC_EXPR) pop();
                                                                            return RIGHT_PAREN;
                                                                        }

    "]" / {CONTINUE_ATOMIC_CODE_IF}                                     { popMode(MARKUP); return RIGHT_BRACKET; }
    "]" / {CONTINUE_NON_ATOMIC_IF}                                      { if(popMode(MARKUP) == EMBEDDED_CODE_EXPR) pop(); return RIGHT_BRACKET; }
    "]" / {SPACE} else                                                  {
                                                                            int was = popMode(MARKUP);
                                                                            if(was == EMBEDDED_CODE_EXPR || was == NON_ATOMIC_EXPR) pop();
                                                                            return RIGHT_BRACKET;
                                                                        }
    "]"                                                                 {
                                                                             int was = popMode(MARKUP);
                                                                             if(was == EMBEDDED_CODE_EXPR || was == NON_ATOMIC_EXPR) pop();
                                                                             else if (was == AFTER_BRANCH) { pop(); pop(); }
                                                                             return RIGHT_BRACKET;
                                                                         }

    "}" / {CONTINUE_ATOMIC_CODE_IF}                                     { popMode(CODE_BLOCK); return RIGHT_BRACE; }
    "}" / {CONTINUE_NON_ATOMIC_IF}                                      { if(popMode(CODE_BLOCK) == EMBEDDED_CODE_EXPR) pop(); return RIGHT_BRACE; }
    "}" / {SPACE} else                                                  {
                                                                            int was = popMode(CODE_BLOCK);
                                                                            if(was == EMBEDDED_CODE_EXPR || was == NON_ATOMIC_EXPR) pop();
                                                                            return RIGHT_BRACE;
                                                                        }
    "}"                                                                 {
                                                                            int was = popMode(CODE_BLOCK);
                                                                            if(was == EMBEDDED_CODE_EXPR || was == NON_ATOMIC_EXPR) pop();
                                                                            else if ( was == AFTER_BRANCH) {pop(); pop();}
                                                                            return RIGHT_BRACE;
                                                                        }
}

<MATH> {
    {MATH_SHORTHANDS}                                                   { return MATH_SHORTHAND; }
    "#"                                                                 { push(EMBEDDED_CODE_EXPR); return HASH; }
    "_"                                                                 { return UNDERSCORE; }
    "$"                                                                 { end_token(); if(pop() == EMBEDDED_CODE_EXPR) pop(); return DOLLAR; }
    "/"                                                                 { return SLASH; }
    "^"                                                                 { return HAT; }
    "'"                                                                 { return PRIME; }
    "&"                                                                 { return MATH_ALIGN_POINT; }
    "√" | "∛" | "∜"                                                     { return ROOT; }
    {MATH_ID_START} {MATH_ID_CONTINUE}*                                 { return MATH_IDENT; }
    [0-9]+(\.[0-9]+)?                                                   { return TEXT; }
    {STRING}                                                            { return STRING; }
    // The hell. Graphemes. TODO.
    [^]                                                                 { return TEXT; }
}

[^]                                                                     { return TokenType.BAD_CHARACTER; }