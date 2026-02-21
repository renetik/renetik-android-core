@file:JvmName("CSLog")
@file:Suppress("NOTHING_TO_INLINE", "unused")

package renetik.android.core.logging

import androidx.annotation.AnyThread
import renetik.android.core.kotlin.CSUnexpectedException
import renetik.android.core.kotlin.toShortString
import renetik.android.core.logging.CSLogLevel.Debug
import renetik.android.core.logging.CSLogLevel.Error
import renetik.android.core.logging.CSLogLevel.Info
import renetik.android.core.logging.CSLogLevel.Verbose
import renetik.android.core.logging.CSLogLevel.Warn
import java.lang.Thread.currentThread
import java.time.Instant
import java.time.ZoneId.systemDefault
import java.time.format.DateTimeFormatter

object CSLog {
    var logger: CSLogger = CSPrintLogger()
        private set

    var isTraceLineEnabled: Boolean = true
        private set

    fun init(logger: CSLogger, isTraceLineEnabled: Boolean) {
        this.logger = logger
        this.isTraceLineEnabled = isTraceLineEnabled
    }

    // -----------------------------------------------------------------------
    // INLINE WRAPPERS (Zero Allocation)
    // -----------------------------------------------------------------------
    @AnyThread
    inline fun log(level: CSLogLevel, crossinline function: () -> Any?) {
        if (logger.isEnabled(level)) log(level, null, function())
    }

    @AnyThread
    inline fun log(level: CSLogLevel, message: Any? = null) {
        if (logger.isEnabled(level)) log(level, null, message)
    }

    @AnyThread @JvmStatic
    inline fun logVerbose(message: Any? = null) {
        if (logger.isEnabled(Verbose)) log(Verbose, null, message)
    }

    @AnyThread @JvmStatic
    inline fun logVerbose(crossinline function: () -> String) {
        if (logger.isEnabled(Verbose)) log(Verbose, null, function())
    }

    @AnyThread @JvmStatic
    inline fun logDebug(crossinline function: () -> String) {
        if (logger.isEnabled(Debug)) log(Debug, null, function())
    }

    @AnyThread @JvmStatic
    inline fun logDebug(message: Any? = null) {
        if (logger.isEnabled(Debug)) log(Debug, null, message)
    }

    @AnyThread @JvmStatic
    inline fun logDebug(throwable: Throwable, crossinline function: (() -> String)) {
        if (logger.isEnabled(Debug)) log(Debug, throwable, function())
    }

    @AnyThread @JvmStatic
    inline fun logDebug(throwable: Throwable) {
        if (logger.isEnabled(Debug)) log(Debug, throwable, "")
    }

    @AnyThread @JvmStatic
    inline fun logDebugTrace(noinline function: (() -> String)? = null) {
        if (logger.isEnabled(Debug)) log(Debug, Throwable(), function?.invoke())
    }

    @AnyThread @JvmStatic
    inline fun logInfo() {
        if (logger.isEnabled(Info)) log(Info, null, "")
    }

    @AnyThread @JvmStatic
    inline fun logInfo(message: Any?) {
        if (logger.isEnabled(Info)) log(Info, null, message)
    }

    @AnyThread @JvmStatic
    inline fun logInfo(crossinline function: () -> String) {
        if (logger.isEnabled(Info)) log(Info, null, function())
    }

    @AnyThread @JvmStatic
    inline fun logInfo(throwable: Throwable, message: String) {
        if (logger.isEnabled(Info)) log(Info, throwable, message)
    }

    @AnyThread @JvmStatic
    inline fun logInfo(throwable: Throwable, noinline function: (() -> String)? = null) {
        if (logger.isEnabled(Info)) log(Info, throwable, function?.invoke())
    }

    inline fun logInfoTrace(message: Any? = null, skip: Int = 0, length: Int = 5) {
        if (logger.isEnabled(Info)) {
            log(Info, null, "$message\n" + Throwable().toShortString(skip, length))
        }
    }

    @AnyThread @JvmStatic
    inline fun logInfoTrace(crossinline function: () -> String) {
        if (logger.isEnabled(Info)) {
            log(Info, Throwable(), function())
        }
    }

    @AnyThread @JvmStatic
    inline fun logWarn() {
        if (logger.isEnabled(Warn)) log(Warn, null, "")
    }

    @AnyThread @JvmStatic
    inline fun logWarn(message: Any?) {
        if (logger.isEnabled(Warn)) log(Warn, null, message)
    }

    @AnyThread @JvmStatic
    inline fun logWarn(crossinline function: () -> String) {
        if (logger.isEnabled(Warn)) log(Warn, null, function())
    }

    @AnyThread @JvmStatic
    inline fun logWarn(throwable: Throwable?, noinline function: (() -> String)? = null) {
        if (logger.isEnabled(Warn)) log(Warn, throwable, function?.invoke())
    }

    @AnyThread @JvmStatic
    inline fun logWarn(throwable: Throwable?, message: String?) {
        if (logger.isEnabled(Warn)) log(Warn, throwable, message)
    }

    @AnyThread @JvmStatic
    inline fun logWarnTrace(crossinline function: () -> String) {
        if (logger.isEnabled(Warn)) {
            log(Warn, Throwable(), function())
        }
    }

    @AnyThread @JvmStatic
    inline fun logError() {
        if (logger.isEnabled(Error)) log(Error, null, "")
    }

    @AnyThread @JvmStatic
    inline fun logError(message: String?) {
        if (logger.isEnabled(Error)) log(Error, null, message)
    }

    @AnyThread @JvmStatic
    inline fun logError(crossinline function: () -> String) {
        if (logger.isEnabled(Error)) log(Error, null, function())
    }

    @AnyThread @JvmStatic
    inline fun logError(throwable: Throwable?) {
        if (logger.isEnabled(Error)) log(Error, throwable, "")
    }

    @AnyThread @JvmStatic
    inline fun logError(throwable: Throwable?, message: String?) {
        if (logger.isEnabled(Error)) log(Error, throwable, message)
    }

    @AnyThread @JvmStatic
    inline fun logErrorTrace() {
        if (logger.isEnabled(Error)) log(Error, Throwable(), "")
    }

    @AnyThread @JvmStatic
    inline fun logErrorTrace(crossinline function: () -> String) {
        if (logger.isEnabled(Error)) log(Error, Throwable(), function())
    }

    // -----------------------------------------------------------------------
    // FAST LOGGING (Zero Allocation / No Side Effects)
    // -----------------------------------------------------------------------
    @AnyThread @JvmStatic
    inline fun logVerboseFast(crossinline function: () -> String) {
        if (logger.isEnabled(Verbose)) logger.verbose(null, function())
    }

    @AnyThread @JvmStatic
    inline fun logDebugFast(crossinline function: () -> String) {
        if (logger.isEnabled(Debug)) logger.debug(null, function())
    }

    @AnyThread @JvmStatic
    inline fun logInfoFast(crossinline function: () -> String) {
        if (logger.isEnabled(Info)) logger.info(null, function())
    }

    @AnyThread @JvmStatic
    inline fun logWarnFast(crossinline function: () -> String) {
        if (logger.isEnabled(Warn)) logger.warn(null, function())
    }

    @AnyThread @JvmStatic
    inline fun logWarnFast(message: String) {
        if (logger.isEnabled(Warn)) logger.warn(null, message)
    }

    @AnyThread @JvmStatic
    inline fun logWarnFast(ex: Throwable) {
        if (logger.isEnabled(Warn)) logger.warn(ex, null)
    }

    @AnyThread @JvmStatic
    inline fun logWarnFast(ex: Throwable, message: String) {
        if (logger.isEnabled(Warn)) logger.warn(ex, null)
    }

    @AnyThread @JvmStatic
    inline fun logErrorFast(crossinline function: () -> String) {
        if (logger.isEnabled(Error)) logger.error(null, function())
    }

    // -----------------------------------------------------------------------
    // INTERNAL IMPLEMENTATION (The Anchor)
    // -----------------------------------------------------------------------

    /**
     * This is the "Anchor" method. It is NOT inline.
     * It will always appear in the stack trace, allowing us to find the caller reliably.
     */
    @PublishedApi @AnyThread
    internal fun log(level: CSLogLevel, throwable: Throwable? = null, message: Any? = "") {
        val time = dateTimeFormatter.format(Instant.now())
        val text = createMessage(message?.toString() ?: "", time)
        when (level) {
            Verbose -> logger.verbose(throwable, text)
            Debug -> logger.debug(throwable, text)
            Info -> logger.info(throwable, text)
            Warn -> logger.warn(throwable, text)
            Error -> logger.error(throwable, text)
            else -> CSUnexpectedException.unexpected()
        }
    }

    private inline fun createMessage(text: String, time: String): String? =
        if (isTraceLineEnabled) {
            if (text.isNotBlank()) "$time ${traceLine()} $text"
            else "$time ${traceLine()}"
        } else {
            if (text.isNotBlank()) "$time $text" else time
        }

    private val dateTimeFormatter = DateTimeFormatter
        .ofPattern("yyyy-MM-dd HH:mm:ss").withZone(systemDefault())

    /**
     * The first frame that isn't CSLog is the caller.
     */
    private fun traceLine(): String {
        val stackTrace = currentThread().stackTrace
        val logClassName = CSLog::class.java.name
        for (index in stackTrace.indices) {
            val element = stackTrace[index]
            val className = element.className
            val isLoggerInternal = className.startsWith(logClassName) ||
                    className == "java.lang.Thread" ||
                    className == "dalvik.system.VMStack"
            if (!isLoggerInternal) {
                return "${element.className}$${element.methodName}(${element.fileName}:${element.lineNumber})"
            }
        }
        return "UnknownSource"
    }
}