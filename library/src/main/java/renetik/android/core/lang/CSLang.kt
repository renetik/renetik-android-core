package renetik.android.core.lang

import renetik.android.core.logging.CSLog.logInfo
import kotlin.system.measureNanoTime

object CSLang {
    val EmptyFunc = {}

    fun <T> measureAndLog(title: String, func: () -> T): T {
        var value by notNull<T>()
        val elapsedMs = measureNanoTime { value = func() } / 1_000_000
        logInfo { "measure $title took $elapsedMs ms" }
        return value
    }
}

typealias CSFloatRange = ClosedFloatingPointRange<Float>

inline fun <T : Any, R> synchronized(lock: T, block: (T) -> R): R =
    kotlin.synchronized(lock) { block(lock) }

inline fun invoke(block: () -> Unit): Unit = block()