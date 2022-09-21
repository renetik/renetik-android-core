package renetik.android.core.kotlin.primitives

import renetik.android.core.lang.void
import java.lang.System.nanoTime
import kotlin.math.absoluteValue
import kotlin.random.Random

inline val Int.Companion.Empty get() = MAX_VALUE

fun Int.Companion.unique(length: Int = 9): Int {
    val nanoTimeString = "${nanoTime()}"
    val startIndex: Int = nanoTimeString.length - length
    val uniqueInt: String = nanoTimeString.substring(startIndex, startIndex + length)
    return uniqueInt.toInt()
}

fun Int.Companion.random(min: Int = 0, max: Int = MAX_VALUE): Int {
    if (min >= max) throw IllegalArgumentException("max must be greater than min")
    return Random.nextInt(max - min + 1) + min
}

inline val Int.isEmpty get() = this == Int.Empty
inline val Int.isSet get() = !this.isEmpty
fun Int.max(maximumValue: Int) = if (this < maximumValue) this else maximumValue
fun Int.min(minimumValue: Int) = if (this > minimumValue) this else minimumValue

infix fun Int.isFlagSet(bitwise: Int) = bitwise and this != 0
infix fun Int.isFlafNotSet(bitwise: Int) = !this.isFlagSet(bitwise)

val Int.isFirstIndex get() = this == 0
val Int.asIndex get() = this - 1
fun Int.isLastIndex(index: Int) = index == this - 1

inline val Int.isEven: Boolean get() = this % 2 == 0
inline val Int.isOdd: Boolean get() = !isEven

inline fun Int.update(
    newCount: Int, onAdd: (index: Int) -> void, onRemove: (index: Int) -> void) {
    val lastIndex = this - 1
    val difference = newCount - this
    if (difference > 0) repeat(difference) { onAdd(lastIndex + 1 + it) }
    else repeat(difference.absoluteValue) { onRemove(lastIndex - it) }
}
