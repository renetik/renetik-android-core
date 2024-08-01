package renetik.android.core.common

import android.content.Context
import android.graphics.Color
import android.graphics.Color.BLACK
import android.graphics.Color.BLUE
import android.graphics.Color.CYAN
import android.graphics.Color.DKGRAY
import android.graphics.Color.GRAY
import android.graphics.Color.GREEN
import android.graphics.Color.LTGRAY
import android.graphics.Color.MAGENTA
import android.graphics.Color.RED
import android.graphics.Color.TRANSPARENT
import android.graphics.Color.WHITE
import android.graphics.Color.YELLOW
import android.graphics.Color.parseColor
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import renetik.android.core.lang.CSHasId

data class CSColor(@ColorInt val color: Int) : CSHasId {

    constructor(hex: String) : this(parseColor(hex))

    fun toHex(): String = String.format("#%06X", (0xFFFFFF and color))


    init {
        BLACK
    }

    companion object {

        val standard = listOf(
            BLACK, DKGRAY, GRAY, LTGRAY, WHITE, RED, GREEN,
            BLUE, YELLOW, CYAN, MAGENTA, TRANSPARENT
        ).map(::CSColor)


        fun generateLightColors(count: Int): List<Int> {
            val colors = mutableListOf<Int>()
            for (i in 0 until count) {
                val r = (200 + (Math.random() * 55)).toInt() // R between 200 and 255
                val g = (200 + (Math.random() * 55)).toInt() // G between 200 and 255
                val b = (200 + (Math.random() * 55)).toInt() // B between 200 and 255
                val color = Color.rgb(r, g, b)
                colors.add(color)
            }

            return colors
        }

        fun Context.colorRes(@ColorRes colorRes: Int) = CSColor(
            ContextCompat.getColor(this, colorRes))

        fun Context.colorInt(@ColorInt colorInt: Int) = CSColor(colorInt)
    }

    override val id: String = toHex()
}