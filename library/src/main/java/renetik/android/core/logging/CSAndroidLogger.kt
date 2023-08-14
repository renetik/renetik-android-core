package renetik.android.core.logging

import android.util.Log
import renetik.android.core.BuildConfig.LIBRARY_PACKAGE_NAME
import renetik.android.core.kotlin.primitives.Space
import renetik.android.core.kotlin.text.add
import renetik.android.core.lang.CSEnvironment.isDebug
import renetik.android.core.logging.CSLogLevel.Debug
import renetik.android.core.logging.CSLogLevel.Error
import renetik.android.core.logging.CSLogLevel.Info
import renetik.android.core.logging.CSLogLevel.Warn


class CSAndroidLogger(
    val tag: String = LIBRARY_PACKAGE_NAME,
    override val level: CSLogLevel = if (isDebug) Debug else Error,
    val listener: CSLogListener? = null
) : CSLogger {

    override fun debug(e: Throwable?, vararg values: Any?) {
        if (isDisabled(Debug)) return
        val message = values.createMessage().toString()
        Log.d(tag, message, e)
        listener?.invoke(Debug, message, e)
    }

    override fun info(e: Throwable?, vararg values: Any?) {
        if (isDisabled(Info)) return
        val message = values.createMessage().toString()
        Log.i(tag, message, e)
        listener?.invoke(Info, message, e)
    }

    override fun warn(e: Throwable?, vararg values: Any?) {
        if (isDisabled(Warn)) return
        val message = values.createMessage().toString()
        Log.w(tag, message, e)
        listener?.invoke(Warn, message, e)
    }

    override fun error(e: Throwable?, vararg values: Any?) {
        if (isDisabled(Error)) return
        val message = values.createMessage().toString()
        Log.e(tag, message, e)
        listener?.invoke(Error, message, e)
    }

    private fun Array<out Any?>.createMessage() = StringBuilder().also { builder ->
        forEach { it?.let { builder.add(it).add(String.Space) } }
    }
}
