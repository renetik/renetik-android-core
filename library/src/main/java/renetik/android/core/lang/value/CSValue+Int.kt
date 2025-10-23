@file:Suppress("NOTHING_TO_INLINE")

package renetik.android.core.lang.value

inline operator fun Int.plus(value: CSValue<Int>): Int = this + value.value
inline operator fun CSValue<Int>.plus(value: CSValue<Int>): Int = value.value + value.value
inline operator fun CSValue<Int>.plus(value: Int): Int = this.value + value
inline operator fun CSValue<Int>.plus(value: Float): Float = this.value + value

inline operator fun Int.minus(value: CSValue<Int>): Int = this - value.value
inline operator fun CSValue<Int>.minus(value: Int): Int = this.value - value
inline operator fun CSValue<Int>.minus(value: Float): Float = this.value - value
inline operator fun CSValue<Int>.minus(value: CSValue<Int>): Int = this.value - value.value

inline operator fun Int.times(value: CSValue<Int>): Int = this * value.value
inline operator fun CSValue<Int>.times(value: Int): Int = this.value * value
inline operator fun CSValue<Int>.times(value: Float): Float = this.value * value
inline operator fun CSValue<Int>.times(value: CSValue<Int>): Int = this.value * value.value
inline operator fun CSValue<Int>.times(value: CSValue<Float>): Float = this.value * value.value

inline operator fun CSValue<Int>.div(value: Int): Int = this.value / value
inline operator fun CSValue<Int>.div(value: Float): Float = this.value / value

inline operator fun CSValue<Int>.compareTo(value: Int): Int = this.value.compareTo(value)
inline operator fun CSValue<Int>.compareTo(value: CSValue<Int>): Int =
    this.value.compareTo(value.value)
