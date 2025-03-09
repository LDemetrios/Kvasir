@file:Suppress("PackageDirectoryMismatch", "unused")
@file:JvmName("Constants")

package org.ldemetrios.utilities.constants

val NEWLINES = arrayOf(
    /* LF:  Line Feed,           */ '\u000A',
    /* VT:  Vertical Tab,        */ '\u000B',
    /* FF:  Form Feed,           */ '\u000C',
    /* CR:  Carriage Return,     */ '\u000D',
    /* NEL: Next Line,           */ '\u0085',
    /* LS:  Line Separator,      */ '\u2028',
    /* PS:  Paragraph Separator, */ '\u2029',
)

val NEWLINE_PATTERN = Regex("\r\n|[\\u000A\\u000B\\u000C\\u000D\\u0085\\u2028\\u2029]")