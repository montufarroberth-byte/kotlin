// RUN_PIPELINE_TILL: FRONTEND
// LANGUAGE: +ContextParameters
// ISSUE: KT-82878

// FILE: a.kt
package a

fun bar() {}

// FILE: b.kt
package b

context(s: String)
fun bar() {}

// FILE: test.kt
import a.*
import b.*

fun foo() {}

fun baz() {}

context(s: String)
<!CONTEXTUAL_OVERLOAD_SHADOWED("fun baz(): Unit")!>fun baz()<!> {}

fun test() {
    context(s: String)
    fun foo() {} // (2)

    ::<!CALLABLE_REFERENCE_TO_CONTEXTUAL_DECLARATION!>foo<!>

    ::<!OVERLOAD_RESOLUTION_AMBIGUITY!>bar<!>

    ::<!OVERLOAD_RESOLUTION_AMBIGUITY!>baz<!>
}

/* GENERATED_FIR_TAGS: callableReference, functionDeclaration, functionDeclarationWithContext, localFunction */
