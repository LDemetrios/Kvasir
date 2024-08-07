package org.ldemetrios.kvasir.syntax;

import com.intellij.psi.tree.IElementType;
import org.ldemetrios.kvasir.language.TypstLanguage;

public class ErroneousTypes extends IElementType {
    public ErroneousTypes(String debugName) {
        super(debugName, TypstLanguage.getINSTANCE());
    }

    public static final TypstTokenType UNEXPECTED_BLOCK_COMMENT = new TypstTokenType("UNEXPECTED_BLOCK_COMMENT");
    public static final TypstTokenType UNCLOSED_BLOCK_COMMENT = new TypstTokenType("UNCLOSED_BLOCK_COMMENT");
    public static final TypstTokenType EMPTY_LINK = new TypstTokenType("EMPTY_LINK");
    public static final TypstTokenType UNCLOSED_UNICODE_ESCAPE = new TypstTokenType("UNCLOSED_UNICODE_ESCAPE");
    public static final TypstTokenType UNCLOSED_LABEL = new TypstTokenType("UNCLOSED_LABEL");
    public static final TypstTokenType UNBALANCED_LINK = new TypstTokenType("UNBALANCED_LINK");
    public static final TypstTokenType UNREACHABLE = new TypstTokenType("UNREACHABLE");
    public static final TypstTokenType UNCLOSED_RAW = new TypstTokenType("UNCLOSED_RAW");
    public static final TypstTokenType NON_ATOMICS_ARE_NOT_SUPPORTED = new TypstTokenType("NON_ATOMICS_ARE_NOT_SUPPORTED");
    public static final TypstTokenType RAW_TOO_LONG_DELIM = new TypstTokenType("RAW_TOO_LONG_DELIM");
    public static final TypstTokenType UNEXPECTED_CLOSING_BRACKET = new TypstTokenType("UNEXPECTED_CLOSING_BRACKET");
    public static final TypstTokenType UNCLOSED_MATH = new TypstTokenType("UNCLOSED_MATH");
    public static final TypstTokenType LINE_COMMENT = new TypstTokenType("LINE_COMMENT");
    public static final TypstTokenType BLOCK_COMMENT = new TypstTokenType("BLOCK_COMMENT");

    public static final TypstTokenType[] ERROR_LIST = {UNEXPECTED_BLOCK_COMMENT,
            UNCLOSED_BLOCK_COMMENT,
            EMPTY_LINK,
            UNCLOSED_UNICODE_ESCAPE,
            UNCLOSED_LABEL,
            UNBALANCED_LINK,
            UNREACHABLE,
            UNCLOSED_RAW,
            NON_ATOMICS_ARE_NOT_SUPPORTED,
            RAW_TOO_LONG_DELIM,
            UNEXPECTED_CLOSING_BRACKET,
            UNCLOSED_MATH
    };
}
