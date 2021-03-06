package renetik.android.core.kotlin

import renetik.android.core.lang.CSHasId
import kotlin.properties.ObservableProperty
import kotlin.reflect.KProperty

inline fun <T, R> onNotNull(receiver: T?, block: T.() -> R) {
    receiver?.let { receiver.block() }
}

inline fun <T : Any> T.run(function: (T) -> Unit): Unit = function(this)

inline fun <T : Any> T.runIf(condition: Boolean, function: (T) -> T) =
    if (condition) function(this) else this

//Interesting but I don't use it: by observing(...
fun <T> observing(value: T, willSet: (T) -> Unit = { }, didSet: (T) -> Unit = { }) =
    object : ObservableProperty<T>(value) {
        override fun beforeChange(property: KProperty<*>, oldValue: T, newValue: T): Boolean {
            willSet(newValue); return true
        }

        override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) =
            didSet(oldValue)
    }

inline val <T : Any> T.className get() = this::class.simpleName

fun Any?.toId() = (this as? CSHasId)?.id ?: toString()