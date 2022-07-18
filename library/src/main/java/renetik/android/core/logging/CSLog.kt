package renetik.android.core.logging

import android.content.Context
import renetik.android.core.extensions.content.toast
import renetik.android.core.kotlin.CSUnexpectedException
import renetik.android.core.lang.ReturnFunc
import renetik.android.core.logging.CSLogLevel.*
import renetik.android.core.logging.CSLogMessage.Companion.Empty
import java.lang.System.currentTimeMillis
import java.lang.Thread.currentThread
import java.text.DateFormat.getDateTimeInstance

object CSLog {
    var logger: CSLogger = CSPrintLogger()

    fun init(logger: CSLogger) {
        this.logger = logger
    }

    fun log(level: CSLogLevel, function: () -> CSLogMessage = { Empty }) = logImpl(level, function)
    fun logDebug(function: () -> CSLogMessage = { Empty }) = logImpl(Debug, function)
    fun logInfo(function: ReturnFunc<CSLogMessage> = { Empty }) = logImpl(Info, function)
    fun logWarn(function: () -> CSLogMessage = { Empty }) = logImpl(Warn, function)
    fun logError(function: () -> CSLogMessage = { Empty }) = logImpl(Error, function)

    fun Context.logInfoToast(function: ReturnFunc<CSLogMessage> = { Empty }) {
        logImpl(Info, function)
        if (logger.isEnabled(Warn)) toast(function().message)
    }

    fun Context.logWarnToast(message: ReturnFunc<CSLogMessage> = { Empty }) {
        logImpl(Warn, message)
        if (logger.isEnabled(Warn)) toast(message().message)
    }

    fun Context.logErrorToast(message: ReturnFunc<CSLogMessage> = { Empty }) {
        logImpl(Error, message)
        if (logger.isEnabled(Warn)) toast(message().message)
    }

    private fun logImpl(level: CSLogLevel, message: () -> CSLogMessage = { Empty }) {
        if (logger.isEnabled(level)) message().let {
            val text = createMessage(it.message)
            when (level) {
                Debug -> logger.debug(it.throwable, *text)
                Info -> logger.info(it.throwable, *text)
                Warn -> logger.warn(it.throwable, *text)
                Error -> logger.error(it.throwable, *text)
                else -> CSUnexpectedException.unexpected()
            }
        }
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