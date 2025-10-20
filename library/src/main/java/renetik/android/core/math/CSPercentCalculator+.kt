@file:Suppress("NOTHING_TO_INLINE")

package renetik.android.core.math

import kotlin.math.roundToInt

inline fun CSPercentCalculator.toValue(percent: Number): Double =
    toValue(percent.toDouble())

inline fun CSPercentCalculator.toValueFloat(percent: Number): Float =
    toValue(percent.toDouble()).toFloat()

inline fun CSPercentCalculator.toValueInt(percent: Number): Int =
    toValue(percent.toDouble()).roundToInt()

inline fun CSPercentCalculator.toPercent(value: Number): Double =
    toPercent(value.toDouble())

inline fun CSPercentCalculator.toPercentFloat(value: Number): Float =
    toPercent(value.toDouble()).toFloat()

inline fun CSPercentCalculator.toPercentInt(value: Number): Int =
    toPercent(value.toDouble()).roundToInt()

inline fun CSPercentCalculator.size(size: Int) {
    this.size = size.toDouble()
}

inline fun CSPercentCalculator.size(size: Double) {
    this.size = size
}

