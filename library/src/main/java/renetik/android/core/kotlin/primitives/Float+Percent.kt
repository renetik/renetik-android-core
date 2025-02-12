@file:Suppress("NOTHING_TO_INLINE")

package renetik.android.core.kotlin.primitives

import renetik.android.core.kotlin.ranges.first
import renetik.android.core.kotlin.ranges.size
import kotlin.math.abs

inline fun Float.percentOf(size: Float): Float = this * size / 100f
inline fun Float.percentOf(size: Int): Float = percentOf(size.toFloat())
inline fun Float.percentOf(size: Long): Float = percentOf(size.toFloat())
inline fun Float.percentOfInt(size: Float): Int = percentOf(size).toInt()
inline fun Float.percentOfInt(size: Int): Int = percentOf(size.toFloat()).toInt()

inline fun Float.percentOf(range: ClosedRange<Float>): Float {
    val size = range.size.takeIf { it > 0 } ?: return 0f
    return range.first + percentOf(size)
}

@JvmName("percentOfRangeInt")
inline fun Float.percentOf(range: ClosedRange<Int>): Float {
    val size = range.size.takeIf { it > 0 } ?: return 0f
    return range.first + percentOf(size)
}

inline fun Float.toPercentOf(total: Float): Float {
    if (total == 0f) return 0f
    if (this <= 0f) return 0f
    if (this >= total) return 100f
    return (this / total * 100)
}

inline fun Float.toPercentOf(total: Int): Float = toPercentOf(total.toFloat())
inline fun Float.toPercentOf(range: ClosedRange<Float>): Float =
    (this - range.first).toPercentOf(range.size)