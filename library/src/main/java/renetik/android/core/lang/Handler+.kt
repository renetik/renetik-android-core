package renetik.android.core.lang

import android.os.Handler
import kotlin.time.Duration

fun Handler.send(function: Fun) {
    post(function)
}

fun Handler.send(after: Duration, function: () -> Unit) =
    postDelayed(function, after.inWholeMilliseconds)

fun Handler.send(after: Int = 0, function: () -> Unit) =
    postDelayed(function, after.toLong())

fun Handler.send(after: Long, function: () -> Unit) {
    postDelayed(function, after)
}

