package renetik.android.core.kotlin

import java.util.*

val Throwable.rootCauseMessage get() = rootCause?.message

val Throwable.rootCause: Throwable?
    get() {
        var throwable: Throwable? = this
        val list = ArrayList<Throwable>()
        while (throwable != null && !list.contains(throwable)) {
            list.add(throwable)
            throwable = throwable.cause
        }
        return if (list.size < 2) null else list[list.size - 1]
    }
