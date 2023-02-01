package renetik.android.core.lang

interface CSFunc {
    operator fun invoke()

    companion object {
        inline fun func(
            crossinline function: () -> void
        ): CSFunc = object : CSFunc {
            override fun invoke() = function()
        }

        val emptyFunc = func {}
    }
}