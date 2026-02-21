@file:JvmName("CSLog")
@file:Suppress("NOTHING_TO_INLINE", "unused")

package renetik.android.core.logging

import androidx.annotation.AnyThread
import renetik.android.core.kotlin.CSUnexpectedException
import renetik.android.core.kotlin.applyIf
import renetik.android.core.kotlin.primitives.Space
import renetik.android.core.kotlin.text.add
import renetik.android.core.kotlin.toShortString
import renetik.android.core.lang.CSEnvironment.isDebug
import renetik.android.core.logging.CSLogLevel.Debug
import renetik.android.core.logging.CSLogLevel.Error
import renetik.android.core.logging.CSLogLevel.Info
import renetik.android.core.logging.CSLogLevel.Verbose
import renetik.android.core.logging.CSLogLevel.Warn
import renetik.android.core.logging.CSLogMessage.Companion.message
import renetik.android.core.logging.CSLogMessage.Companion.traceMessage
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
    inline fun log(level: CSLogLevel, crossinline function: () -> CSLogMessage) {
        if (logger.isEnabled(level)) printLog(level, function())
    }

    @AnyThread @JvmStatic
    inline fun logVerbose(any: Any? = null) {
        if (logger.isEnabled(Verbose)) printLog(Verbose, message(any))
    }

    @AnyThread @JvmStatic
    inline fun logVerbose(crossinline function: () -> String) {
        if (logger.isEnabled(Verbose)) printLog(Verbose, message(function()))
    }

    @AnyThread @JvmStatic
    inline fun logDebug(crossinline function: () -> String) {
        if (logger.isEnabled(Debug)) printLog(Debug, message(function()))
    }

    @AnyThread @JvmStatic
    inline fun logDebug(any: Any?) {
        if (logger.isEnabled(Debug)) printLog(Debug, message(any))
    }

    @AnyThread @JvmStatic
    fun logDebug() {
        if (logger.isEnabled(Debug)) printLog(Debug, message())
    }

    @AnyThread @JvmStatic
    inline fun logDebug(throwable: Throwable, crossinline function: (() -> String)) {
        if (logger.isEnabled(Debug)) printLog(Debug, message(throwable, function()))
    }

    @AnyThread @JvmStatic
    inline fun logDebug(throwable: Throwable) {
        if (logger.isEnabled(Debug)) printLog(Debug,
            message(throwable))
    }

    @AnyThread @JvmStatic
    inline fun logDebugTrace(noinline function: (() -> String)? = null) {
        if (logger.isEnabled(Debug)) printLog(Debug,
            message(Throwable(), function?.invoke()))
    }

    @AnyThread @JvmStatic
    inline fun logInfo() {
        if (logger.isEnabled(Info)) printLog(Info, message(""))
    }

    @AnyThread @JvmStatic
    inline fun logInfo(any: Any?) {
        if (logger.isEnabled(Info)) printLog(Info, message(any))
    }

    @AnyThread @JvmStatic
    inline fun logInfo(crossinline function: () -> String) {
        if (logger.isEnabled(Info)) printLog(Info, message(function()))
    }

    @AnyThread @JvmStatic
    inline fun logInfo(throwable: Throwable, message: String) {
        if (logger.isEnabled(Info)) printLog(Info, message(throwable, message))
    }

    @AnyThread @JvmStatic
    inline fun logInfo(throwable: Throwable, noinline function: (() -> String)? = null) {
        if (logger.isEnabled(Info)) printLog(Info, message(throwable, function?.invoke()))
    }

    @AnyThread @JvmStatic
    inline fun logInfoTrace(any: Any? = null, skip: Int = 0, length: Int = 5) {
        if (logger.isEnabled(Info)) {
            printLog(Info, message("$any\n" + Throwable().toShortString(skip, length)))
        }
    }

    @AnyThread @JvmStatic
    inline fun logInfoTrace(crossinline function: () -> String) {
        if (logger.isEnabled(Info)) {
            if (isDebug) printLog(Info, traceMessage(function()))
            else printLog(Info, message(Throwable(), function()))
        }
    }

    @AnyThread @JvmStatic
    inline fun logWarn() {
        if (logger.isEnabled(Warn)) printLog(Warn, message(""))
    }

    @AnyThread @JvmStatic
    inline fun logWarn(any: Any?) {
        if (logger.isEnabled(Warn)) printLog(Warn, message(any))
    }

    @AnyThread @JvmStatic
    inline fun logWarn(crossinline function: () -> String) {
        if (logger.isEnabled(Warn)) printLog(Warn, message(function()))
    }

    @AnyThread @JvmStatic
    inline fun logWarn(throwable: Throwable?, noinline function: (() -> String)? = null) {
        if (logger.isEnabled(Warn)) printLog(Warn, message(throwable, function?.invoke()))
    }

    @AnyThread @JvmStatic
    inline fun logWarn(throwable: Throwable?, message: String?) {
        if (logger.isEnabled(Warn)) printLog(Warn, message(throwable, message))
    }

    @AnyThread @JvmStatic
    inline fun logWarnTrace(crossinline function: () -> String) {
        if (logger.isEnabled(Warn)) {
            if (isDebug) printLog(Warn, traceMessage(function()))
            else printLog(Warn, message(Throwable(), function()))
        }
    }

    @AnyThread @JvmStatic
    inline fun logError() {
        if (logger.isEnabled(Error)) printLog(Error, message(""))
    }

    @AnyThread @JvmStatic
    inline fun logError(message: String?) {
        if (logger.isEnabled(Error)) printLog(Error, message(message))
    }

    @AnyThread @JvmStatic
    inline fun logError(crossinline function: () -> String) {
        if (logger.isEnabled(Error)) printLog(Error, message(function()))
    }

    @AnyThread @JvmStatic
    inline fun logError(throwable: Throwable?) {
        if (logger.isEnabled(Error)) printLog(Error, message(throwable))
    }

    @AnyThread @JvmStatic
    inline fun logError(throwable: Throwable?, message: String?) {
        if (logger.isEnabled(Error)) printLog(Error, message(throwable, message))
    }

    @AnyThread @JvmStatic
    inline fun logErrorTrace() {
        if (logger.isEnabled(Error)) printLog(Error, message(Throwable()))
    }

    @AnyThread @JvmStatic
    inline fun logErrorTrace(crossinline function: () -> String) {
        if (logger.isEnabled(Error)) printLog(Error, message(Throwable(), function()))
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
    internal fun printLog(level: CSLogLevel, msg: CSLogMessage): Array<String?> {
        val time = dateTimeFormatter.format(Instant.now())
        val values = if (isTraceLineEnabled) arrayOf(time, getTraceLine(), msg.message)
        else arrayOf(time, msg.message)
        val message  = values.createMessage()
        when (level) {
            Verbose -> logger.verbose(msg.throwable, message)
            Debug -> logger.debug(msg.throwable, message)
            Info -> logger.info(msg.throwable, message)
            Warn -> logger.warn(msg.throwable, message)
            Error -> logger.error(msg.throwable, message)
            else -> CSUnexpectedException.unexpected()
        }
        return values
    }

    private fun Array<out Any?>.createMessage(): String {
        if (size == 1) {
            val single = this[0]
            if (single is String) return single
        }
        return StringBuilder().also { builder ->
            forEachIndexed { index, it ->
                it?.toString()?.takeIf(String::isNotBlank)?.let {
                    builder.applyIf(index != 0) { add(String.Space) }.add(it)
                }
            }
        }.toString()
    }

    private val dateTimeFormatter = DateTimeFormatter
        .ofPattern("yyyy-MM-dd HH:mm:ss").withZone(systemDefault())

    /**
     * The first frame that isn't CSLog is the caller.
     */
    private fun getTraceLine(): String {
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