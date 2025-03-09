package org.ldemetrios.kvasir.misc

import com.intellij.lang.Commenter

class TypstCommenter : Commenter {
    override fun getLineCommentPrefix(): String? = "//"

    override fun getBlockCommentPrefix(): String? = "/*"

    override fun getBlockCommentSuffix(): String? = "*/"

    override fun getCommentedBlockCommentPrefix(): String? = "/*"

    override fun getCommentedBlockCommentSuffix(): String? = "*/"
}

