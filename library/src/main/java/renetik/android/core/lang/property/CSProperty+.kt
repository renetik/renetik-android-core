package renetik.android.core.lang.property

import renetik.android.core.kotlin.asString

fun <T> CSProperty<T>.value(value: T) = apply { this.value = value }

fun CSProperty<Boolean>.setTrue() = apply { this.value = true }
fun CSProperty<Boolean>.setFalse() = apply { this.value = false }
fun CSProperty<Boolean>.toggle() = apply { value = !value }
inline var CSProperty<Boolean>.isTrue
    get() = value
    set(newValue) {
        value = newValue
    }

inline var CSProperty<Boolean>.isFalse
    get() = !value
    set(newValue) {
        value = !newValue
    }


inline var CSProperty<String?>.string
    get() = value.asString
    set(newValue) {
        value = newValue
    }

fun <T : CSProperty<Int>> T.increment() = apply { value++ }
fun <T : CSProperty<Int>> T.decrement() = apply { value-- }

fun CSProperty<Double>.value(value: Int) = apply { this.value = value.toDouble() }
fun CSProperty<Float>.value(value: Number) = apply { this.value = value.toFloat() }

