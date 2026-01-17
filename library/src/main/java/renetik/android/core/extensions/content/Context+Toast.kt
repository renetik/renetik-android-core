package renetik.android.core.extensions.content

import android.content.Context
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import renetik.android.core.extensions.content.CSToastTime.ShortTime
import renetik.android.core.java.lang.isThreadMain
import renetik.android.core.lang.CSEnvironment.app
import renetik.android.core.lang.CSHandler.mainHandler
import renetik.android.core.lang.send

object CSToast {
    fun toast(text: String) = app.toast(text)

    fun toast(text: String, time: CSToastTime = ShortTime) =
        app.toast(text, time)
}

enum class CSToastTime(val value: Int) {
    ShortTime(LENGTH_SHORT), LongTime(LENGTH_LONG)
}

fun Context.toast(text: String) = toast(text, time = ShortTime)

fun Context.toast(text: String, time: CSToastTime = ShortTime) {
    fun toast() = Toast.makeText(this, text, time.value).show()
    if (isThreadMain) toast() else mainHandler.send(::toast)
}
