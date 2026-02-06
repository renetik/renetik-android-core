@file:JvmName("CSLog")
@file:Suppress("NOTHING_TO_INLINE", "unused")

package renetik.android.core.logging

import android.content.Context
import renetik.android.core.extensions.content.toast
import renetik.android.core.kotlin.CSUnexpectedException
import renetik.android.core.kotlin.primitives.leaveEndOfLength
import renetik.android.core.kotlin.toShortString
import renetik.android.core.lang.CSEnvironment.isDebug
import renetik.android.core.lang.CSStringConstants.NewLine
import renetik.android.core.logging.CSLogLevel.Debug
import renetik.android.core.logging.CSLogLevel.Error
import renetik.android.core.logging.CSLogLevel.Info
import renetik.android.core.logging.CSLogLevel.Verbose
import renetik.android.core.logging.CSLogLevel.Warn
import renetik.android.core.logging.CSLogMessage.Companion.message
import renetik.android.core.logging.CSLogMessage.Companion.traceMessage
import java.lang.System.currentTimeMillis
import java.lang.Thread.currentThread
import java.text.DateFormat.getDateTimeInstance

object CSLog {
    var logger: CSLogger = CSPrintLogger()

    fun init(logger: CSLogger) {
        this.logger = logger
    }

    // -----------------------------------------------------------------------
    // INLINE WRAPPERS (Zero Allocation)
    // -----------------------------------------------------------------------
    inline fun log(level: CSLogLevel, crossinline function: () -> CSLogMessage) {
        if (logger.isEnabled(level)) printLog(level, function())
    }

    inline fun logVerbose(any: Any? = null) {
        if (logger.isEnabled(Verbose)) printLog(Verbose, message(any))
    }

    inline fun logVerbose(crossinline function: () -> String) {
        if (logger.isEnabled(Verbose)) printLog(Verbose, message(function()))
    }

    @JvmStatic
    inline fun logDebug(crossinline function: () -> String) {
        if (logger.isEnabled(Debug)) printLog(Debug, message(function()))
    }

    @JvmStatic
    inline fun logDebug(any: Any?) {
        if (logger.isEnabled(Debug)) printLog(Debug, message(any))
    }

    @JvmStatic
    fun logDebug() {
        if (logger.isEnabled(Debug)) printLog(Debug, message())
    }

    @JvmStatic
    inline fun logDebug(throwable: Throwable, crossinline function: (() -> String)) {
        if (logger.isEnabled(Debug)) printLog(Debug, message(throwable, function()))
    }

    @JvmStatic
    inline fun logDebug(throwable: Throwable) {
        if (logger.isEnabled(Debug)) printLog(Debug,
            message(throwable))
    }

    @JvmStatic
    inline fun logDebugTrace(noinline function: (() -> String)? = null) {
        if (logger.isEnabled(Debug)) printLog(Debug,
            message(Throwable(), function?.invoke()))
    }

    @JvmStatic
    inline fun logInfo() {
        if (logger.isEnabled(Info)) printLog(Info, message(""))
    }

    @JvmStatic
    inline fun logInfo(any: Any?) {
        if (logger.isEnabled(Info)) printLog(Info, message(any))
    }

    @JvmStatic
    inline fun logInfo(crossinline function: () -> String) {
        if (logger.isEnabled(Info)) printLog(Info, message(function()))
    }

    @JvmStatic
    inline fun logInfo(throwable: Throwable, message: String) {
        if (logger.isEnabled(Info)) printLog(Info, message(throwable, message))
    }

    @JvmStatic
    inline fun logInfo(throwable: Throwable, noinline function: (() -> String)? = null) {
        if (logger.isEnabled(Info)) printLog(Info, message(throwable, function?.invoke()))
    }

    @JvmStatic
    inline fun logInfoTrace(any: Any? = null, skip: Int = 0, length: Int = 5) {
        if (logger.isEnabled(Info)) {
            printLog(Info, message("$any\n" + Throwable().toShortString(skip, length)))
        }
    }

    @JvmStatic
    inline fun logInfoTrace(crossinline function: () -> String) {
        if (logger.isEnabled(Info)) {
            if (isDebug) printLog(Info, traceMessage(function()))
            else printLog(Info, message(Throwable(), function()))
        }
    }

    @JvmStatic
    inline fun logWarn() {
        if (logger.isEnabled(Warn)) printLog(Warn, message(""))
    }

    @JvmStatic
    inline fun logWarn(any: Any?) {
        if (logger.isEnabled(Warn)) printLog(Warn, message(any))
    }

    @JvmStatic
    inline fun logWarn(crossinline function: () -> String) {
        if (logger.isEnabled(Warn)) printLog(Warn, message(function()))
    }

    @JvmStatic
    inline fun logWarn(throwable: Throwable?, noinline function: (() -> String)? = null) {
        if (logger.isEnabled(Warn)) printLog(Warn, message(throwable, function?.invoke()))
    }

    @JvmStatic
    inline fun logWarn(throwable: Throwable?, message: String?) {
        if (logger.isEnabled(Warn)) printLog(Warn, message(throwable, message))
    }

    @JvmStatic
    inline fun logWarnTrace(crossinline function: () -> String) {
        if (logger.isEnabled(Warn)) {
            if (isDebug) printLog(Warn, traceMessage(function()))
            else printLog(Warn, message(Throwable(), function()))
        }
    }

    @JvmStatic
    inline fun logError() {
        if (logger.isEnabled(Error)) printLog(Error, message(""))
    }

    @JvmStatic
    inline fun logError(message: String?) {
        if (logger.isEnabled(Error)) printLog(Error, message(message))
    }

    @JvmStatic
    inline fun logError(crossinline function: () -> String) {
        if (logger.isEnabled(Error)) printLog(Error, message(function()))
    }

    @JvmStatic
    inline fun logError(throwable: Throwable?) {
        if (logger.isEnabled(Error)) printLog(Error, message(throwable))
    }

    @JvmStatic
    inline fun logError(throwable: Throwable?, message: String?) {
        if (logger.isEnabled(Error)) printLog(Error, message(throwable, message))
    }

    @JvmStatic
    inline fun logErrorTrace() {
        if (logger.isEnabled(Error)) printLog(Error, message(Throwable()))
    }

    @JvmStatic
    inline fun logErrorTrace(crossinline function: () -> String) {
        if (logger.isEnabled(Error)) printLog(Error, message(Throwable(), function()))
    }

    // -----------------------------------------------------------------------
    // FAST LOGGING (Zero Allocation / No Side Effects)
    // -----------------------------------------------------------------------

    @JvmStatic
    inline fun logVerboseFast(crossinline function: () -> String) {
        if (logger.isEnabled(Verbose)) logger.verbose(null, function())
    }

    @JvmStatic
    inline fun logDebugFast(crossinline function: () -> String) {
        if (logger.isEnabled(Debug)) logger.debug(null, function())
    }

    @JvmStatic
    inline fun logInfoFast(crossinline function: () -> String) {
        if (logger.isEnabled(Info)) logger.info(null, function())
    }

    @JvmStatic
    inline fun logWarnFast(crossinline function: () -> String) {
        if (logger.isEnabled(Warn)) logger.warn(null, function())
    }

    @JvmStatic
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
    @PublishedApi
    internal fun printLog(level: CSLogLevel, msg: CSLogMessage): Array<Any?> {
        val text = createMessage(msg.message)
        when (level) {
            Verbose -> logger.verbose(msg.throwable, *text)
            Debug -> logger.debug(msg.throwable, *text)
            Info -> logger.info(msg.throwable, *text)
            Warn -> logger.warn(msg.throwable, *text)
            Error -> logger.error(msg.throwable, *text)
            else -> CSUnexpectedException.unexpected()
        }
        return text
    }

    fun Context.toast(level: CSLogLevel, text: Array<Any?>?) {
        if (text != null && logger.isEnabled(level)) {
            toast("${text[2]}".leaveEndOfLength(100).chunked(50)
                .joinToString { "$it$NewLine" })
        }
    }

    private val timeFormat by lazy { getDateTimeInstance() }

    private fun createMessage(message: Any?) = Array(3) {
        when (it) {
            0 -> timeFormat.format(currentTimeMillis())
            1 -> getTraceLine()
            else -> message
        }
    }

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