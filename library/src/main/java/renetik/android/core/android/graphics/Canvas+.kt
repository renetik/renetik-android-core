package renetik.android.core.android.graphics

import android.graphics.Canvas
import android.graphics.Paint

fun Canvas.line(startX: Float, startY: Float, stopX: Float, stopY: Float, paint: Paint) =
    drawLine(startX, startY, stopX, stopY, paint)