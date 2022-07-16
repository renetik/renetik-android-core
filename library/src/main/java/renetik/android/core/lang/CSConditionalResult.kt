package renetik.android.core.lang

class CSConditionalResult(private val doElseIf: Boolean) {
    fun elseDo(function: Func) {
        if (doElseIf) function()
    }
}