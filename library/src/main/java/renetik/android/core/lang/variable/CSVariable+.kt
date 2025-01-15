@file:Suppress("NOTHING_TO_INLINE")

package renetik.android.core.lang.variable

import renetik.android.core.kotlin.asString
import renetik.android.core.lang.value.CSValue

inline infix fun <T> CSVariable<T>.assign(other: T) = value(other)

inline infix fun <T> CSVariable<T>?.assign(other: T) = this?.value(other)

inline infix fun <T> CSVariable<T>?.assign(other: CSValue<T>){
    this?.value(other.value)
}

inline fun <T> CSVariable<T>.value(value: T) {
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

