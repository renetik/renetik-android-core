package renetik.android.core.lang.variable

fun CSVariable<Boolean>.setTrue() = apply { this.value = true }
fun CSVariable<Boolean>.setFalse() = apply { this.value = false }
fun CSVariable<Boolean>.toggle() = apply { value = !value }

inline var CSVariable<Boolean>.isTrue: Boolean
    get() = value
    set(newValue) {
        value = newValue
    }

inline var CSVariable<Boolean>.isFalse: Boolean
    get() = !value
    set(newValue) {
        value = !newValue
    }