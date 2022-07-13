package renetik.android.core.math

import kotlin.math.min
import kotlin.math.roundToInt

class CSPercentCalculator(
    val min: Float = 0f, max: Float = 0f) {
    constructor(min: Int = 0, max: Int) : this(min.toFloat(), max.toFloat())
    constructor(size: Float) : this(0f, size)
    constructor(size: Int) : this(0, size)

    var size = max - min
        set(value) {
            field = value
            onePercent = calculateOnePercent()
        }

    private var onePercent: Float = calculateOnePercent()

    private fun calculateOnePercent(): Float = size / 100f

    fun percentToValue(percent: Int): Float = min + (percent * onePercent)

    fun percentToValue(percent: Float): Float = min + (percent * onePercent)

    fun valueToPercent(value: Float): Int =
        if (value <= min) 0 else min((value - min) / onePercent, 100f).roundToInt()

    fun valueToPercentFloat(value: Int): Float =
        if (value <= min) 0f else min((value - min) / onePercent, 100f)

    fun valueToPercentFloat(value: Float): Float =
        if (value <= min) 0f else min((value - min) / onePercent, 100f)
}