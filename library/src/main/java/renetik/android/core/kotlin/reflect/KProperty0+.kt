package renetik.android.core.kotlin.reflect

import renetik.android.core.lang.lazy.CSLazyProperty
import renetik.android.core.logging.CSLog.logWarn
import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible

val KProperty0<*>.isInitialized: Boolean
    get() = (getDelegate() as? CSLazyProperty)?.isInitialized
        ?: (getDelegate() as? Lazy<*>)?.isInitialized()
        ?: run {
            logWarn { "No isInitialized, Not a CSLazyProperty or Lazy" }
            true
        }

fun KProperty0<*>.reset() {
    isAccessible = true //Needed for "Cannot obtain the delegate of a non-accessible property."
    (getDelegate() as? CSLazyProperty)?.reset() ?: logWarn { "No reset, not a CSLazyProperty" }
}