package org.ldemetrios.tyko


import org.ldemetrios.tyko.compiler.*
import org.ldemetrios.tyko.ffi.TypstSharedLibrary
import org.ldemetrios.tyko.model.*
import org.ldemetrios.tyko.operations.where
import kotlin.io.path.Path

private const val SHARED_LIBRARY_PATH = "/home/ldemetrios/Workspace/TypstNKotlinInterop/libtypst_shared.so"

private val sharedLib = TypstSharedLibrary.instance(Path(SHARED_LIBRARY_PATH))

fun main() {
//    val world = WorldBasedTypstCompiler(sharedLib, DetachedWorld())
//    val lim = 1
//    for (i in 0 until lim) {
//        world.evalDetached("1 + 2")
//    }
//    val source = """
//        #set heading(numbering: "1.")
//
//        = Fibonacci sequence
//        The Fibonacci sequence is defined through the
//        recurrence relation.
//        It can also be expressed in _closed form._
//
//        #let count = 8
//        #let nums = range(1, count + 1)
//        #let fib(n) = (
//            if n <= 2 { 1 }
//            else { fib(n - 1) + fib(n - 2) }
//        )
//
//        The first #count numbers of the sequence are: #(nums.map(fib).map(str).join(", "))
//    """.trimIndent()
//    val world = WorldBasedTypstCompiler(sharedLib, SingleFileWorld(source, Feature.ALL))
//
//    println(world.compileHtml())

    val source = """
    #show raw.where(lang: "typ") : it => [
        #metadata(it) <x>
        #it
    ]

    Parentheses are smartly resolved, so you can enter your expression as
    you would into a calculator and Typst will replace parenthesized sub-expressions
    with the appropriate notation.

    ```typ
    Total displaced soil by glacial flow:

    $ 7.32 beta + sum_(i=0)^nabla (Q_i (a_i - epsilon)) / 2 $
    ```
    Preview
    Not all math constructs have special syntax.
    Instead, we use functions, just like the image function we have seen before.
    For example, to insert a column vector, we can use the vec function.
    Within math mode, function calls don't need to start with the \# character.
    ```typ
    $ v := vec(x_1, x_2, x_3) $
    ```
"""

    val compiler = WorldBasedTypstCompiler(
        sharedLib, SingleFileWorld(source, Feature.ALL)
    )

    val code = compiler.query(TLabel("x".t))
        .map { it as TMetadata<*> }
        .map { it.value as TRaw }
        .map { compiler.evalDetached("[\n" + it.text + "\n]") }

    TSequence(
        TArray(
        code.flatMap { listOf(it as TContent, TParbreak()) }
    ))

}