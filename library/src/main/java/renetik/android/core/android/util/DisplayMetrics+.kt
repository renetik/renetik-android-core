package renetik.android.core.android.util

import android.util.DisplayMetrics
import renetik.android.core.base.CSApplication.Companion.app
import renetik.android.core.extensions.content.isLandscape

val DisplayMetrics.orientedWidthPixels: Int
    get() = if (app.isLandscape) heightPixels else widthPixels

val DisplayMetrics.orientedHeightPixels: Int
    get() = if (app.isLandscape) widthPixels else heightPixels