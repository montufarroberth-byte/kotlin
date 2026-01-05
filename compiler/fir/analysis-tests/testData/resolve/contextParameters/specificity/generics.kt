// RUN_PIPELINE_TILL: FRONTEND
// LANGUAGE: +ContextParameters +ExplicitContextArguments

class A
class B

fun expectA(vararg a: A) {}
fun expectB(vararg b: B) {}

interface Box<T>

open class Ca
open class Cb : Ca()



// generic vs non-contextual
fun foo0() = A()

context(t: T)
fun <T> foo0() = B()

// generic vs non-generic
context(s: String)
fun foo1() = A()

context(t: T)
fun <T> foo1() = B()

// generic vs non-generic with argument
context(s: String)
fun foo2(argS: String) = A()

context(t: T)
fun <T> foo2(argT: T) = B()

// partially generic vs non-generic
context(s: String, i: Int)
fun foo3() = A()

context(t: T, i: Int)
fun <T> foo3() = B()

context(i: Int)
fun test0() {
    val a0 = foo0() // should be ambigous?
    val b1 = foo1()
    val b4 = foo2(42)
    val b2 = foo3()
    expectA(a0)
    expectB(b1, b2)
}

context(s: String)
fun test1() {
    val a0 = foo0() // should be ambigous?
    val a1 = foo1() // should be ambigous?
    val b2 = foo1<String>()
    expectA(a0, a1)
    expectB(b2)
}

context(s: String, i: Int)
fun test2() {
    val a0 = foo0() // should be ambigous?
    val a1 = foo1() // should be ambigous?
    val b2 = foo1<String>()
    val a3 = foo2("")
    val b4 = foo2(<!ARGUMENT_TYPE_MISMATCH!>42<!>) // should be ok?
    val a5 = foo3() // should be ambigous?
    val b6 = foo3<String>()
    expectA(a0, a1, a3, a5)
    expectB(b2, b6)
}


// deep generic vs non-generic
context(_: Box<String>)
fun bar1() = A()

context(_: Box<T>)
fun <T> bar1() = B()

// deep generic with arguments
context(_: Box<T>)
fun <T> bar2(element: Int?) = A()

context(_: Box<T>)
fun <T> bar2(vararg elements: Int?) = B()

// deep generic vs non-generic with argument
context(_: Box<String>)
fun bar3(s: String) = A()

context(_: Box<T>)
fun <T> bar3(t: T) = B()

// generic vs bounded generic
context(_: Box<T>)
fun <T> bar4() = A()

context(_: Box<T>)
<!CONTEXTUAL_OVERLOAD_SHADOWED!>fun <T : Cb> bar4()<!> = B()

context(b: Box<String>)
fun test3() {
    val a0 = bar1() // should be ambigous?

    val b1 = bar2()
    val a2 = bar2(1)
    val a3 = bar2(null)
    val b4 = bar2(1, 2)
    val b5 = bar2(1, null)
    bar2(<!ARGUMENT_TYPE_MISMATCH!>true<!>)

    val a6 = bar3("")
    bar3(<!ARGUMENT_TYPE_MISMATCH!>42<!>)

    expectA(a0, a2, a3, a6)
    expectB(b1, b4, b5)
}

context(b: Box<Int>)
fun test4() {
    val b0 = bar1()

    <!NO_CONTEXT_ARGUMENT!>bar3<!>("")
    val b1 = bar3(42)

    expectB(b0, b1)
}

context(b: Box<Ca>)
fun test5() {
    bar4()
}

context(b: Box<Cb>)
fun test6() {
    <!OVERLOAD_RESOLUTION_AMBIGUITY!>bar4<!>()
}

/* GENERATED_FIR_TAGS: functionDeclaration, functionDeclarationWithContext, integerLiteral, interfaceDeclaration,
nullableType, typeParameter, vararg */
