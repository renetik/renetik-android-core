package renetik.android.core.extensions.content

import android.content.Context
import android.view.Gravity.CENTER_HORIZONTAL
import android.view.Gravity.TOP
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import renetik.android.core.extensions.content.CSToastLocation.Bottom
import renetik.android.core.extensions.content.CSToastLocation.Top
import renetik.android.core.extensions.content.CSToastTime.ShortTime
import renetik.android.core.java.lang.isThreadMain
import renetik.android.core.kotlin.primitives.dp
import renetik.android.core.lang.CSEnvironment.app
import renetik.android.core.lang.CSHandler.mainHandler
import renetik.android.core.lang.send


object CSToast {
    fun toast(text: String) = app.toast(text)
    fun toast(text: String, time: CSToastTime = ShortTime) =
        app.toast(text, time)
}

enum class CSToastLocation { Bottom, Top }

enum class CSToastTime(val value: Int) {
    LongTime(LENGTH_LONG), ShortTime(LENGTH_SHORT)
}

fun Context.toast(text: String) = toast(text, time = ShortTime)

fun Context.toast(
    text: String,
    time: CSToastTime = ShortTime,
    location: CSToastLocation = Bottom,
) {
    fun toast() = makeText(this, text, time.value).apply {
        if (location == Top) setGravity(TOP or CENTER_HORIZONTAL, 0, 100.dp)
    }.show()
    if (isThreadMain) toast() else mainHandler.send(::toast)
}

