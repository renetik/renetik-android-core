package renetik.android.core.kotlin.reflect

import renetik.android.core.lang.lazy.CSLazyProperty
import renetik.android.core.logging.CSLog.logWarn
import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible

val KProperty0<*>.isInitialized: Boolean
    get() {
        isAccessible = true //for "Cannot obtain the delegate of a non-accessible property."
        return when (val delegate = getDelegate()) {
            is CSLazyProperty<*> -> delegate.isInitialized
            is Lazy<*> -> delegate.isInitialized()
            else -> {
                logWarn { "No isInitialized, Not a CSLazyProperty or Lazy" }
                true
            }
        }
    }

@Suppress("UNCHECKED_CAST")
val <T> KProperty0<T>.lazyValue: T?
    get() {
        isAccessible = true //for "Cannot obtain the delegate of a non-accessible property."
        return (getDelegate() as? CSLazyProperty<T>)?.value
            ?: (getDelegate() as? Lazy<T>)?.let {
                if (it.isInitialized()) it.value else null
            }
            ?: run {
                logWarn { "No isInitialized, Not a CSLazyProperty or Lazy" }
                null
            }
    }

fun KProperty0<*>.reset() {
    isAccessible = true //for "Cannot obtain the delegate of a non-accessible property."
    (getDelegate() as? CSLazyProperty<*>)?.reset()
        ?: logWarn { "No reset, not a CSLazyProperty" }
}