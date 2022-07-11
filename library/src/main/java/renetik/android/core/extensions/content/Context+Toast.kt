package renetik.android.core.extensions.content

import android.content.Context
import android.widget.Toast.*
//import renetik.android.core.lang.CSEnvironment.app
import renetik.android.core.extensions.content.CSToastLength.LongTime
import renetik.android.core.extensions.content.CSToastLength.ShortTime
import renetik.android.core.lang.CSEnvironment.app

object CSToast {
    fun toast(text: String) = app.toast(text)
    fun toast(text: String, time: CSToastLength = ShortTime) = app.toast(text, time)
}

fun Context.toast(text: String) = toast(text, LongTime)

fun Context.toast(text: String, time: CSToastLength = ShortTime) =
    makeText(this, text, time.value).show()

enum class CSToastLength(val value: Int) {
    LongTime(LENGTH_LONG), ShortTime(LENGTH_SHORT)
}