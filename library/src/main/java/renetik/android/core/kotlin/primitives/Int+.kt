@file:Suppress("NOTHING_TO_INLINE")

package renetik.android.core.kotlin.primitives

import renetik.android.core.extensions.content.dpToPixel
import renetik.android.core.extensions.content.dpToPixelF
import renetik.android.core.extensions.content.spToPixelF
import renetik.android.core.extensions.content.toDp
import renetik.android.core.extensions.content.toDpF
import renetik.android.core.lang.ArgFunc
import renetik.android.core.lang.CSEnvironment.app
import renetik.android.core.lang.CSTimeConstants.Minute
import renetik.android.core.lang.CSTimeConstants.Second
import java.lang.System.nanoTime
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.absoluteValue
import kotlin.random.Random

operator fun Int.minus(other: Int?): Int = this - (other ?: 0)
operator fun Int.plus(other: Int?): Int = this + (other ?: 0)


private val counter = AtomicInteger(0)
fun Int.Companion.unique(length: Int = 9): Int =
    "${counter.incrementAndGet()}${nanoTime()}".substring(0, length).toInt()

fun Int.Companion.random(min: Int = 0, max: Int = MAX_VALUE): Int {
    if (min >= max) throw IllegalArgumentException("max must be greater than min")
    return Random.nextInt(max - min + 1) + min
}

fun Int.max(maximumValue: Int) = if (this < maximumValue) this else maximumValue
fun Int.min(minimumValue: Int) = if (this > minimumValue) this else minimumValue

infix fun Int.isFlagSet(bitwise: Int) = bitwise and this != 0
infix fun Int.isFlagNotSet(bitwise: Int) = !this.isFlagSet(bitwise)

val Int.isFirstIndex get() = this == 0
val Int.asIndex get() = this - 1
fun Int.isLastIndex(index: Int) = index == this - 1

inline val Int.isEven: Boolean get() = this % 2 == 0
inline val Int.isOdd: Boolean get() = !isEven

fun Int.update(
    newCount: Int, onAdd: ArgFunc<Int>? = null, onRemove: ArgFunc<Int>? = null,
) {
    val lastIndex = this - 1
    val difference = newCount - this
    if (difference > 0) repeat(difference) { onAdd?.invoke(lastIndex + 1 + it) }
    else repeat(difference.absoluteValue) { onRemove?.invoke(lastIndex - it) }
}

inline val Int.dp: Int get() = app.dpToPixel(this)
inline val Int.px: Int get() = app.toDp(this)
inline val Int.dpf: Float get() = app.dpToPixelF(this)
inline val Int.spf: Float get() = app.spToPixelF(this)
inline val Int.pxf: Float get() = app.toDpF(this)
inline val Int.second: Int get() = this * Second
inline val Int.minute: Int get() = this * Minute

inline fun Int.nextPowerOfTwo(): Int {
    if (this <= 0) return 1
    return if (this and (this - 1) == 0) this
    else Integer.highestOneBit(this) shl 1
}

inline fun Int.nearestPowerOfTwo(): Int {
    if (this <= 0) return 1
    return Integer.highestOneBit(this)
}
