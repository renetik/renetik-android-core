package renetik.android.core.lang

@Deprecated("Remove")
@JvmInline
value class CSConditionalResult(private val doElseIf: Boolean) {
    fun elseDo(function: Func) {
        if (doElseIf) function()
    }
}