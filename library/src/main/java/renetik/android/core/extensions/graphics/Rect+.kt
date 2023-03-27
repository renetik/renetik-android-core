package renetik.android.core.extensions.graphics

import android.graphics.Rect
import renetik.android.core.math.CSPoint
import kotlin.math.max
import kotlin.math.min

val Rect.width get() = width()

val Rect.height get() = height()

val Rect.debugString get() = "left:$left top:$top width:$width height:$height"

fun Rect.load(start: CSPoint<Float>, end: CSPoint<Float>) = apply {
    val left = min(start.x, end.x)
    val top = min(start.y, end.y)
    val right = max(start.x, end.x)
    val bottom = max(start.y, end.y)
    set(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
}

fun Rect.clear() = set(0, 0, 0, 0)