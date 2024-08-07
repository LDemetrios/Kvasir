//package org.ldemetrios.kvasir.syntax
//
//import com.intellij.psi.PsiElement
//import com.intellij.psi.util.elementType
//import com.intellij.testFramework.ParsingTestCase
//import java.io.BufferedWriter
//import java.io.File
//
//val tests = File("src/test/resources/original/suite")
//    .walkTopDown()
//    .filter { it.isFile }
//    .map { it.readText() }
//    .filter { !it.startsWith("// SKIP") }
//    .flatMap {
//        it.split("\n---")
//    }
//    .map {
//        it.split("---", limit = 2)
//    }
//    .filterNot {
//        it.size == 1 && it[0].matches(Regex("((\\s*//[^\n\r]*)?[\n\r])+(\\s*//[^\r\n]*)?"))
//    }
//    .filterNot { it[0].isBlank() }
//    .onEach {
//        if (it.size != 2) {
//            for (el in it) {
//                println(el)
//                println("\n")
//            }
//            println("\n\n\n\n")
//        }
//    }
//    .filter { it.size == 2 }
//    .map { it[0].trim() to it[1] }
//    .toList()
//
//class SimpleParsingTest : ParsingTestCase("", "typ", TypstParserDefinition()) {
//    fun testParsingTestData() {
//        val folder = File("src/test/output/parsing")
//        folder.run {
//            if (exists()) deleteRecursively()
//            mkdirs()
//        }
//        var erroneous = false
//        for ((name, test) in tests) {
//            folder.resolve("$name.typ").writeText(test)
//            val output = folder.resolve("$name.typ.out").bufferedWriter()
//            println("--- $name ---")
//            try {
//                val file = parseFile("$name.typ", test)
//                flush(file, output)
//            } catch (e: Throwable) {
//                e.printStackTrace(System.out)
//                erroneous = true
//            }
//            output.close()
//        }
//        if (erroneous) throw RuntimeException("Some tests failed")
//    }
//
//    override fun includeRanges(): Boolean {
//        return true
//    }
//
//    override fun getTestDataPath() = "src/test/original/suite"
//}
//
//private const val TAB = "    "
//
//private fun flush(node: PsiElement, output: BufferedWriter, indent: Int = 0) {
//    var cur: PsiElement? = node.firstChild
//    while (cur != null) {
//        if (cur.elementType == TypstTypes.WSPACE) {
//            cur = cur.nextSibling
//            continue
//        }
//        output.write(TAB.repeat(indent))
//        output.write(cur.elementType.toString())
//        if (cur.firstChild != null && cur.elementType != TypstTypes.JUST_TEXT) {
//            output.write(":\n")
//            flush(cur, output, indent + 1)
//        } else {
//            output.write(": ")
//            output.write(cur.text.replace("\n", "\\n").replace("\r", "\\r"))
//            output.write("\n")
//        }
//        cur = cur.nextSibling
//    }
//
//
//}
