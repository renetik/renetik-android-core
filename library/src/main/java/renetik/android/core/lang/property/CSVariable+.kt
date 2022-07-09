package renetik.android.core.lang.property

import renetik.android.core.kotlin.asString

fun <T> CSVariable<T>.value(value: T) = apply { this.value = value }

fun CSVariable<Boolean>.setTrue() = apply { this.value = true }
fun CSVariable<Boolean>.setFalse() = apply { this.value = false }
fun CSVariable<Boolean>.toggle() = apply { value = !value }
inline var CSVariable<Boolean>.isTrue
    get() = value
    set(newValue) {
        value = newValue
    }

inline var CSVariable<Boolean>.isFalse
    get() = !value
    set(newValue) {
        value = !newValue
    }


inline var CSVariable<String?>.string
    get() = value.asString
    set(newValue) {
        value = newValue
    }

fun <T : CSVariable<Int>> T.increment() = apply { value++ }
fun <T : CSVariable<Int>> T.decrement() = apply { value-- }

fun CSVariable<Double>.value(value: Int) = apply { this.value = value.toDouble() }
fun CSVariable<Float>.value(value: Number) = apply { this.value = value.toFloat() }

