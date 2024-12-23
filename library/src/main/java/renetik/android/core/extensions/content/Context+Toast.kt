package renetik.android.core.extensions.content

import android.content.Context
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import renetik.android.core.extensions.content.CSToastLength.LongTime
import renetik.android.core.extensions.content.CSToastLength.ShortTime
import renetik.android.core.java.lang.isThreadMain
import renetik.android.core.lang.CSEnvironment.app
import renetik.android.core.lang.CSHandler.mainHandler
import renetik.android.core.lang.send

object CSToast {
    fun toast(text: String) = app.toast(text)
    fun toast(text: String, time: CSToastLength = ShortTime) = app.toast(text, time)
}

fun Context.toast(text: String) = toast(text, LongTime)

fun Context.toast(text: String, time: CSToastLength = ShortTime) {
    fun toast() = makeText(this, text, time.value).show()
    if (isThreadMain) toast() else mainHandler.send(::toast)
}

enum class CSToastLength(val value: Int) {
    LongTime(LENGTH_LONG), ShortTime(LENGTH_SHORT)
}