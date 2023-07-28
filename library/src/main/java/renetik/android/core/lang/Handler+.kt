package renetik.android.core.lang

import android.os.Handler

fun Handler.send(function: Func) {
    post(function)
}

fun Handler.send(after: Int = 0, function: () -> Unit) =
    postDelayed(function, after.toLong())

fun Handler.send(after: Long, function: () -> Unit) {
    postDelayed(function, after)
}

