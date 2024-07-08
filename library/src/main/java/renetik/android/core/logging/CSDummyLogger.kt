package renetik.android.core.logging

import renetik.android.core.logging.CSLogLevel.Debug
import renetik.android.core.logging.CSLogLevel.Error
import renetik.android.core.logging.CSLogLevel.Info
import renetik.android.core.logging.CSLogLevel.Warn

class CSDummyLogger(
    override val level: CSLogLevel = Debug,
    val listener: ((event: CSLogLevel, message: String) -> Unit)? = null
) : CSLogger {
    override fun verbose(e: Throwable?, vararg values: Any?) {
        listener?.invoke(Debug, values.joinToString { "$it" })
    }

    override fun debug(e: Throwable?, vararg values: Any?) {
        listener?.invoke(Debug, values.joinToString { "$it" })
    }

    override fun info(e: Throwable?, vararg values: Any?) {
        listener?.invoke(Info, values.joinToString { "$it" })
    }

    override fun warn(e: Throwable?, vararg values: Any?) {
        listener?.invoke(Warn, values.joinToString { "$it" })
    }

    override fun error(e: Throwable?, vararg values: Any?) {
        listener?.invoke(Error, values.joinToString { "$it" })
    }
}