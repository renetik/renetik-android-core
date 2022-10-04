package renetik.android.core.extensions.graphics

import android.graphics.Rect

val Rect.width get() = width()
val Rect.height get() = height()
val Rect.debugString get() = "left:$left top:$top width:$width height:$height"