package renetik.android.core.logging

import renetik.android.core.kotlin.primitives.Empty

class CSLogMessage(val throwable: Throwable? = null, val message: String = "") {
    companion object {
        val Empty = CSLogMessage(null, String.Empty)
        fun traceMessage(message: Any? = String.Empty) =
            CSLogMessage(Throwable(), message.toString())

        fun message(message: Any? = String.Empty) = CSLogMessage(null, message.toString())
        fun message(throwable: Throwable) = CSLogMessage(throwable = throwable)
        fun message(throwable: Throwable?, message: String? = String.Empty) =
            CSLogMessage(throwable, message.toString())
    }
}

