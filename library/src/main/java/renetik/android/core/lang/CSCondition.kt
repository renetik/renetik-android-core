package renetik.android.core.lang

interface CSCondition {
    fun evaluate(): Boolean?

    object Factory {
        fun condition(function: () -> Boolean?): CSCondition = CSConditionImplementation(function)
    }
}

private class CSConditionImplementation(val function: () -> Boolean?) : CSCondition {
    override fun evaluate() = function()
}