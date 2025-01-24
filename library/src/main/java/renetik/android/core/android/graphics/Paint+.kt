package renetik.android.core.android.graphics

import android.graphics.Paint

fun Paint(function: (Paint).() -> Unit): Paint =
    Paint(Paint.ANTI_ALIAS_FLAG).also(function)

fun Paint.clone(function: (Paint).() -> Unit): Paint =
    Paint(Paint.ANTI_ALIAS_FLAG).also { it.set(this); function(it) }