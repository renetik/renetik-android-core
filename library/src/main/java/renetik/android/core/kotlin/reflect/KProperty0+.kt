package renetik.android.core.kotlin.reflect

import renetik.android.core.lang.lazy.CSLazyProperty
import renetik.android.core.logging.CSLog.logWarnTrace
import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible

val KProperty0<*>.isInitialized: Boolean
    get() {
        isAccessible = true //for "Cannot obtain the delegate of a non-accessible property."
        return when (val delegate = getDelegate()) {
            is CSLazyProperty<*> -> delegate.isInitialized
            is Lazy<*> -> delegate.isInitialized()
            else -> {
                logWarnTrace { "No isInitialized, Not a CSLazyProperty or Lazy" }; true
            }
        }
    }

val <T> KProperty0<T>.lazyValue: T?
    get() {
        isAccessible = true //for "Cannot obtain the delegate of a non-accessible property."
        return when (val delegate = getDelegate()) {
            is CSLazyProperty<*> -> delegate.value as? T
            is Lazy<*> -> if (delegate.isInitialized()) delegate.value as? T else null
            else -> {
                logWarnTrace { "No isInitialized, Not a CSLazyProperty or Lazy" }; null
            }
        }
    }

fun KProperty0<*>.reset() {
    isAccessible = true //for "Cannot obtain the delegate of a non-accessible property."
    (getDelegate() as? CSLazyProperty<*>)?.reset()
        ?: logWarnTrace { "No reset, not a CSLazyProperty" }
}