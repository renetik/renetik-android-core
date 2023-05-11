package renetik.android.core.math

import kotlin.math.roundToInt

fun CSPercentCalculator.toValueFloat(percent: Number): Float = toValue(percent.toDouble()).toFloat()
fun CSPercentCalculator.toValueInt(percent: Number): Int = toValue(percent.toDouble()).roundToInt()

fun CSPercentCalculator.toPercentFloat(value: Number): Float = toPercent(value.toDouble()).toFloat()
fun CSPercentCalculator.toPercentInt(value: Number): Int = toPercent(value.toDouble()).roundToInt()

fun CSPercentCalculator.size(size: Int) {
    this.size = size.toDouble()
}

fun CSPercentCalculator.size(size: Double) {
    this.size = size
}

