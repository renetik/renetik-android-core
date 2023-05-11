package renetik.android.core.math

import kotlin.math.min

class CSPercentCalculator(val min: Double = 0.0, max: Double = 0.0) {
    constructor(min: Float = 0f, max: Float) : this(min.toDouble(), max.toDouble())
    constructor(min: Int = 0, max: Int) : this(min.toDouble(), max.toDouble())

    var size = max - min
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
