package renetik.android.core.math

import kotlin.math.roundToInt

fun CSPercentCalculator.percentToValueInt(percent: Float): Int = percentToValue(percent).roundToInt()
fun CSPercentCalculator.percentToValueInt(percent: Int): Int = percentToValue(percent).roundToInt()

fun CSPercentCalculator.valueToPercentInt(value: Float): Int = valueToPercent(value)
fun CSPercentCalculator.valueToPercentInt(value: Int): Int = valueToPercentInt(value.toFloat())


fun CSPercentCalculator.size(size: Int) {
    this.size = size.toFloat()
}

fun CSPercentCalculator.size(size: Double) {
    this.size = size.toFloat()
}

