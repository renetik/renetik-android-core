package renetik.android.core.android.graphics

import android.graphics.Paint

fun Paint.clone(function: (Paint).() -> Unit): Paint =
    Paint().also { it.set(this); function(it) }