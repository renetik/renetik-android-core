package renetik.android.core.logging

import renetik.android.core.BuildConfig.LIBRARY_PACKAGE_NAME
import renetik.android.core.kotlin.asTraceString
import renetik.android.core.kotlin.primitives.Space
import renetik.android.core.kotlin.text.add
import renetik.android.core.kotlin.text.deleteLast
import renetik.android.core.logging.CSLogLevel.Debug
import renetik.android.core.logging.CSLogLevel.Error
import renetik.android.core.logging.CSLogLevel.Info
import renetik.android.core.logging.CSLogLevel.Warn

class CSPrintLogger(
    val name: String = LIBRARY_PACKAGE_NAME,
    override val level: CSLogLevel = Debug,
    val listener: CSLogListener? = null
) : CSLogger {

    override fun verbose(e: Throwable?, vararg values: Any?) {
        val message = createMessage(*values)
        println("$Debug: $name: $message $e ${e?.asTraceString ?: ""}")
        println(createPrintMessage(Debug, message, e))
        listener?.invoke(Debug, message.toString(), e)
    }

    override fun debug(e: Throwable?, vararg values: Any?) {
        val message = createMessage(*values)
        println(createPrintMessage(Debug, message, e))
        listener?.invoke(Debug, message.toString(), e)
    }

    override fun info(e: Throwable?, vararg values: Any?) {
        val message = createMessage(*values)
        println(createPrintMessage(Info, message, e))
        listener?.invoke(Info, message.toString(), e)
    }

    override fun warn(e: Throwable?, vararg values: Any?) {
        val message = createMessage(*values)
        println(createPrintMessage(Warn, message, e))
        listener?.invoke(Warn, message.toString(), e)
    }

    override fun error(e: Throwable?, vararg values: Any?) {
        val message = createMessage(*values)
        println(createPrintMessage(Error, message, e))
        listener?.invoke(Error, message.toString(), e)
    }

    private fun createPrintMessage(
        level: CSLogLevel, message: CharSequence, e: Throwable?
    ): String = "$level: $name: $message $e ${e?.asTraceString ?: ""}"

    private fun createMessage(vararg values: Any?): CharSequence {
        if (values.size == 1) {
            val single = values[0]
            if (single is String) return single
        }
        return StringBuilder().apply {
            values.forEach { it?.let { add(it).add(String.Space) } }
        }.deleteLast(String.Space.length)
    }
}
