// RUN_PIPELINE_TILL: FRONTEND
// LANGUAGE: +ContextParameters +ExplicitContextArguments

class A
class B

// scenario 1: base scenario
fun foo1a() { }

context(a: A)
<!CONTEXTUAL_OVERLOAD_SHADOWED!>fun foo1a()<!> { }


context(b: B)
fun foo1b() { }

context(b: B, a: A)
<!CONTEXTUAL_OVERLOAD_SHADOWED!>fun foo1b()<!> { }

context(a: A)
fun foo1c() { }

context(b: B)
fun foo1c() { }

// scenario 2: context vs. value parameter
context(a: A)
fun foo2() { }

fun foo2(a: A) { }

// scenario 3: context vs. optional parameter
context(a: A)
fun foo3a() { }

fun foo3a(a: A = A()) { }


context(b: B, a: A)
fun foo3b() { }

context(b: B)
fun foo3b(a: A = A()) { }

fun test0() {
    foo1a()
    foo1a(a = A())

    <!OVERLOAD_RESOLUTION_AMBIGUITY!>foo2<!>(a = A())

    <!OVERLOAD_RESOLUTION_AMBIGUITY!>foo3a<!>(a = A())
}

context(a: A)
fun test1() {
    <!OVERLOAD_RESOLUTION_AMBIGUITY!>foo1a<!>()
    foo1a(a = A())
    foo1c()

    foo2()
    <!OVERLOAD_RESOLUTION_AMBIGUITY!>foo2<!>(a = A())

    foo3a()
    <!OVERLOAD_RESOLUTION_AMBIGUITY!>foo3a<!>(a = A())
}

context(b: B)
fun test2() {
    foo1c()

    <!OVERLOAD_RESOLUTION_AMBIGUITY!>foo3b<!>(a = A())
}

context(a: A, b: B)
fun test3() {
    <!OVERLOAD_RESOLUTION_AMBIGUITY!>foo1b<!>()
    foo1b(a = A())
    <!OVERLOAD_RESOLUTION_AMBIGUITY!>foo1c<!>()

    foo3b()
    <!OVERLOAD_RESOLUTION_AMBIGUITY!>foo3b<!>(a = A())
}

fun test4(arg: Any) {
    fun <A, R> context(context: A, block: context(A) () -> R): R = block(context)

    foo1a()
    context(arg) {
        foo1a()
    }
    if (arg is A) {
        <!CANNOT_INFER_PARAMETER_TYPE!>context<!>(arg) {
            <!OVERLOAD_RESOLUTION_AMBIGUITY!>foo1a<!>()
        }
    }
}

/* GENERATED_FIR_TAGS: classDeclaration, functionDeclaration, functionDeclarationWithContext */
