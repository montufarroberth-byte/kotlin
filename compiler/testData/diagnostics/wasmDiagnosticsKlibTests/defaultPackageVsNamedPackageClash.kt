// RUN_PIPELINE_TILL: BACKEND
// FIR_IDENTICAL
// FILE: A.kt
@file:Suppress("OPT_IN_USAGE")

<!EXPORTING_JS_NAME_CLASH!>@JsExport fun foo() = 1<!>

// FILE: B.kt
@file:Suppress("OPT_IN_USAGE")
package a

<!EXPORTING_JS_NAME_CLASH!>@JsExport fun foo() = 2<!>
