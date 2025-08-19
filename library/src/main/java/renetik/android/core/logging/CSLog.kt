@file:JvmName("CSLog")

package renetik.android.core.logging

import android.content.Context
import renetik.android.core.extensions.content.toast
import renetik.android.core.kotlin.CSUnexpectedException
import renetik.android.core.kotlin.primitives.leaveEndOfLength
import renetik.android.core.kotlin.then
import renetik.android.core.kotlin.toShortString
import renetik.android.core.lang.CSEnvironment.isDebug
import renetik.android.core.lang.CSStringConstants.NewLine
import renetik.android.core.logging.CSLogLevel.Debug
import renetik.android.core.logging.CSLogLevel.Error
import renetik.android.core.logging.CSLogLevel.Info
import renetik.android.core.logging.CSLogLevel.Verbose
import renetik.android.core.logging.CSLogLevel.Warn
import renetik.android.core.logging.CSLogMessage.Companion.Empty
import renetik.android.core.logging.CSLogMessage.Companion.message
import renetik.android.core.logging.CSLogMessage.Companion.traceMessage
import java.lang.System.currentTimeMillis
import java.lang.Thread.currentThread
import java.text.DateFormat.getDateTimeInstance

//TODO: inline all
object CSLog {
    var logger: CSLogger = CSPrintLogger()

    fun init(logger: CSLogger) {
        this.logger = logger
    }

    fun log(level: CSLogLevel) = then { logImpl(level) { message("") } }
    fun log(level: CSLogLevel, function: () -> CSLogMessage) =
        then { logImpl(level, function) }

    fun logVerbose(any: Any? = null) = then { logImpl(Verbose) { message(any) } }
    fun logVerbose(function: () -> String) =
        then { logImpl(Verbose) { message(function()) } }

    @JvmStatic
    fun logDebug() = then { logImpl(Debug) { message() } }

    @JvmStatic
    fun logDebug(any: Any?) = then { logImpl(Debug) { message(any) } }

    @JvmStatic
    fun logDebug(function: () -> String) = then { logImpl(Debug) { message(function()) } }

    @JvmStatic
    fun logDebug(throwable: Throwable, function: (() -> String)? = null) =
        then { logImpl(Debug) { message(throwable, function?.invoke()) } }

    @JvmStatic
    fun logDebugTrace(function: (() -> String)? = null) =
        then { logImpl(Debug) { message(Throwable(), function?.invoke()) } }

    @JvmStatic
    fun logInfo() = then { logImpl(Info) { message("") } }

    @JvmStatic
    fun logInfo(any: Any?) = then { logImpl(Info) { message(any) } }

    @JvmStatic
    fun logInfo(function: () -> String) = then { logImpl(Info) { message(function()) } }

    @JvmStatic
    fun logInfo(throwable: Throwable, function: (() -> String)? = null) =
        then { logImpl(Info) { message(throwable, function?.invoke()) } }

    fun logInfoTrace(any: Any? = null, skip: Int = 0, length: Int = 5) =
        then {
            logImpl(Info) {
                message(
                    "$any\n" + Throwable().toShortString(skip, length)
                )
            }
        }

    fun logInfoTrace(function: () -> String) = then {
        if (isDebug) logImpl(Info) { traceMessage(function()) }
        else logImpl(Info) { message(Throwable(), function.invoke()) }
    }

    fun logWarn() = then { logImpl(Warn) { message("") } }
    fun logWarn(any: Any?) = then { logImpl(Warn) { message(any) } }
    fun logWarn(function: () -> String) = then { logImpl(Warn) { message(function()) } }
    fun logWarn(throwable: Throwable?, function: (() -> String)? = null) =
        then { logImpl(Warn) { message(throwable, function?.invoke()) } }

    fun logWarnTrace(function: () -> String) = then {
        if (isDebug) logImpl(Warn) { traceMessage(function()) }
        else logImpl(Warn) { message(Throwable(), function.invoke()) }
    }

    fun logError() = then { logImpl(Error) { message("") } }
    fun logError(message: String?) = then { logImpl(Error) { message(message) } }
    fun logError(function: () -> String) = then { logImpl(Error) { message(function()) } }
    fun logError(throwable: Throwable?) = then { logImpl(Error) { message(throwable) } }
    fun logError(throwable: Throwable?, message: String?) =
        then { logImpl(Error) { message(throwable, message) } }

    fun logErrorTrace() = then { logImpl(Error) { message(Throwable()) } }

    fun logErrorTrace(function: () -> String) =
        then { logImpl(Error) { message(Throwable(), function.invoke()) } }

    fun Context.logDebugToast(value: String) =
        toast(Debug, logImpl(Debug) { message(value) })

    fun Context.logDebugToast() = toast(Debug, logImpl(Debug) { message("") })

    fun Context.logDebugStringToast(function: () -> String) =
        toast(Debug, logImpl(Debug) { CSLogMessage(message = function()) })

    fun Context.logDebugToast(function: () -> CSLogMessage) =
        toast(Debug, logImpl(Debug, function))

    fun Context.logInfoToast() = toast(Info, logImpl(Info) { message("") })
    fun Context.logInfoToast(value: String) =
        toast(Info, logImpl(Info) { message(value) })

    fun Context.logInfoToast(function: () -> CSLogMessage) =
        toast(Info, logImpl(Info, function))

    fun Context.logWarnToast() = toast(Warn, logImpl(Warn) { message("") })
    fun Context.logWarnToast(function: () -> CSLogMessage) =
        toast(Warn, logImpl(Warn, function))

    fun Context.logErrorToast() = toast(Error, logImpl(Error) { message("") })
    fun Context.logErrorToast(function: () -> CSLogMessage) =
        toast(Error, logImpl(Error, function))

    private fun logImpl(
        level: CSLogLevel, message: () -> CSLogMessage = { Empty }
    ): Array<Any?>? {
        if (logger.isEnabled(level)) message().let {
            val text = createMessage(it.message)
            when (level) {
                Verbose -> logger.verbose(it.throwable, *text)
                Debug -> logger.debug(it.throwable, *text)
                Info -> logger.info(it.throwable, *text)
                Warn -> logger.warn(it.throwable, *text)
                Error -> logger.error(it.throwable, *text)
                else -> CSUnexpectedException.unexpected()
            }
            return text
        }
        return null
    }

    private fun Context.toast(level: CSLogLevel, text: Array<Any?>?) {
        if (logger.isEnabled(level)) toast(
            "${text!![2]}".leaveEndOfLength(100).chunked(50)
                .joinToString { "$it$NewLine" })
    }

    private val timeFormat by lazy { getDateTimeInstance() }

    fun createMessage(message: Any?) = Array(3) {
        when (it) {
            0 -> timeFormat.format(currentTimeMillis())
            1 -> getTraceLine()
            else -> message
        }
    }

    private fun getTraceLine(): String {
        val stackTrace = currentThread().stackTrace
        val index = stackTrace.indexOfFirst { it.methodName == "logImpl" } + 2
        return stackTrace[index].let { element ->
            "${element.className}$${element.methodName}(${element.fileName}:${element.lineNumber})"
        }
    }
}