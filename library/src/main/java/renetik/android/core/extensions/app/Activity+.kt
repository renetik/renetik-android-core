package renetik.android.core.extensions.app

import android.app.Activity
import android.content.Context
import android.content.res.Configuration.*
import android.view.View
import renetik.android.core.extensions.content.input
import renetik.android.core.logging.CSLog.logDebug
import renetik.android.core.logging.CSLogMessage.Companion.message

val Activity.isWindowLandscape get() = windowOrientation == ORIENTATION_LANDSCAPE

val Activity.windowOrientation: Int
    get() {
        val display = windowManager.defaultDisplay
        return when {
            display.width == display.height -> ORIENTATION_SQUARE
            display.width < display.height -> ORIENTATION_PORTRAIT
            else -> ORIENTATION_LANDSCAPE
        }
    }

@Deprecated("Does this work ?")
fun Context.fixInputMethodLeak() {
    for (declaredField in input::class.java.declaredFields) try {
        if (!declaredField.isAccessible) declaredField.isAccessible = true
        val obj = declaredField.get(input)
        if (obj == null || obj !is View) continue
        if (obj.context === this) declaredField.set(input, null) else continue
    } catch (throwable: Throwable) {
        logDebug { message(throwable) }
    }
}