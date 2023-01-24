package renetik.android.core.math

val <T : Number> CSPoint<T>.left get() = x
val <T : Number> CSPoint<T>.column get() = x
val <T : Number> CSPoint<T>.top get() = y
val <T : Number> CSPoint<T>.row get() = y

infix fun <T : Number, A : T, B : T> A.to(that: B): CSPoint<T> = CSPoint(this, that)