package renetik.android.core.lang

interface CSArgFunc<Arg> {
    operator fun invoke(argument: Arg)

    companion object {
        inline fun <Arg> func(
            crossinline function: (Arg) -> void
        ): CSArgFunc<Arg> = object : CSArgFunc<Arg> {
            override fun invoke(argument: Arg) = function(argument)
        }

        fun <Arg> emptyFunc(): CSArgFunc<Arg> = func { }
    }
}