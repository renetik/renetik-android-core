package renetik.android.core.lang.variable

import renetik.android.core.kotlin.asString

fun <T> CSVariable<T>.value(value: T) {
    this.value = value
}

inline var CSVariable<String?>.string: String
    get() = value.asString
    set(newValue) {
        value = newValue
    }

operator fun CSVariable<String>.plusAssign(value: String) {
    this.value += value
}

operator fun CSVariable<String>.plus(value: String): String = this.value + value

fun CSVariable<Double>.value(value: Int) = apply { this.value = value.toDouble() }

fun CSVariable<Float>.value(value: Number) = apply { this.value = value.toFloat() }

