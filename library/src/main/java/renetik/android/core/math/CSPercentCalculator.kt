package renetik.android.core.math

import kotlin.math.min

@Deprecated("Stop using this!!! Overcomplicated.. ")
class CSPercentCalculator(min: Number = 0.0, max: Number = 0.0) {

    constructor(size: Number = 0.0) : this(max = size)

    val min: Double = min.toDouble()
    var size = max.toDouble() - this.min
        set(value) {
            field = value
            onePercent = calculateOnePercent()
        }

    private var onePercent: Double = calculateOnePercent()
    private fun calculateOnePercent(): Double = size / 100.0

    fun toValue(percent: Double): Double = min + (percent * onePercent)
    fun toPercent(value: Double): Double =
        if (value <= min) 0.0 else min((value - min) / onePercent, 100.0)
}
