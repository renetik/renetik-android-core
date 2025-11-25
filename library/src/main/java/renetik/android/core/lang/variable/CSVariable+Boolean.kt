package renetik.android.core.lang.variable

fun CSVariable<Boolean>.setTrue() = run { this.value = true }
fun CSVariable<Boolean>.setFalse() = run { this.value = false }
fun CSVariable<Boolean>.toggle() = apply { value = !value }

fun CSSafeVariable<Boolean>.toggle() = apply {
    while (true) {
        val current = value
        if (compareAndSet(current, !current)) return@apply
    }
}