package renetik.android.core.kotlin.ranges

val ClosedRange<Float>.size: Float get() = endInclusive - start
val ClosedRange<Int>.size: Int get() = endInclusive - start
val ClosedRange<Int>.from: Int get() = start
val ClosedRange<Int>.to: Int get() = endInclusive
val ClosedRange<Double>.size: Double get() = endInclusive - start