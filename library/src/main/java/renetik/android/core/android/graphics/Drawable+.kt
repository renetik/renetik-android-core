package renetik.android.core.android.graphics

import android.graphics.Color.argb
import android.graphics.Color.blue
import android.graphics.Color.green
import android.graphics.Color.red
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable

fun Drawable.toAlpha(alpha: Double): Drawable =
    (this as? ColorDrawable)?.color?.let {
        ColorDrawable(argb((256 * alpha).toInt(), red(it), green(it), blue(it)))
    } ?: this.also { it.alpha = (255 * alpha).toInt() }