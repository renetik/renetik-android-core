package renetik.android.core.lang.value

fun <T : CSValue<Boolean>?> CSValue.Companion.isAnyTrue(vararg items: T): Boolean {
    items.forEach { if (it?.value == true) return true }
    return false
}