package renetik.android.core.logging

import renetik.android.core.lang.CSEnvironment

class CSDummyLogger() : CSLogger {
    override val isDebug get() = CSEnvironment.isDebug
    override fun error(vararg values: Any?) = Unit
    override fun error(e: Throwable, vararg values: Any?) = Unit
    override fun info(vararg values: Any?) = Unit
    override fun debug(vararg values: Any?) = Unit
    override fun debug(e: Throwable, vararg values: Any?) = Unit
    override fun warn(vararg values: Any?) = Unit
    override fun warn(e: Throwable, vararg values: Any?) = Unit
}