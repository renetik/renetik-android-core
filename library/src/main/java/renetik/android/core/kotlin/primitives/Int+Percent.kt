@file:Suppress("NOTHING_TO_INLINE")

package renetik.android.core.kotlin.primitives

import renetik.android.core.kotlin.ranges.first
import renetik.android.core.kotlin.ranges.size

inline fun Int.percentOf(size: Int): Float = (this * size / 100.0).toFloat()
inline fun Int.percentOf(range: ClosedRange<Int>): Float {
    val size = range.size.takeIf { it > 0 } ?: return 0f
    return range.first + percentOf(size)
}

inline fun Int.toPercentOf(total: Float): Float {
    if (total == 0f) return 0f
    if (this <= 0f) return 0f
    if (this >= total) return 100f
    return (this / total * 100)
}

inline fun Int.toPercentOf(total: Int): Float = toPercentOf(total.toFloat())
inline fun Int.toPercentOfInt(total: Int): Int = toPercentOf(total).toInt()

inline fun Int.toPercentOf(range: ClosedRange<Int>): Float =
    (this - range.first).toPercentOf(range.size)