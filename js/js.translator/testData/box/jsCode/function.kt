package foo
// FILE: a.kt

fun callWithArgs(sumFunc: (Int, Int) -> Int, a: Int, b: Int): Int {
    return sumFunc(a, b)
object Kt {
    fun sum(a: Int, b: Int): Int = a + b
}
object Js {
    val sum = js("function (a, b) { return a + b; }")
}

// FILE: b.kt
// RECOMPILE

fun box(): String {
    val kotlinSum: (Int, Int) -> Int = { a, b -> a + b}
    val jsSum: (Int, Int) -> Int = js("function (a, b) { return a + b; }")
    assertEquals(callWithArgs(kotlinSum, 1, 2), callWithArgs(jsSum, 1, 2))
    assertEquals(Kt.sum(1, 2), Js.sum(1, 2))

    return "OK"
}