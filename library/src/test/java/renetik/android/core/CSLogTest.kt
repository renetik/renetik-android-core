package renetik.android.core

import org.junit.Assert
import org.junit.Test
import renetik.android.core.logging.CSLog.init
import renetik.android.core.logging.CSLog.logDebug
import renetik.android.core.logging.CSLog.logWarn
import renetik.android.core.logging.CSLoggerEvent
import renetik.android.core.logging.CSLoggerEvent.Debug
import renetik.android.core.logging.CSLoggerEvent.Warn
import renetik.android.core.logging.CSPrintLogger

class CSLogTest {
    @Test
    fun warnTestNoInit() {
        logWarn("test")
    }

    @Test
    fun logWithListener() {
        var _event: CSLoggerEvent? = null
        var _message: String? = null
        init(CSPrintLogger(name = "TestLog", isDebug = true) { event, message ->
            _event = event
            _message = message
        })
        logWarn("test")
        Assert.assertEquals(Warn, _event)
        Assert.assertTrue(_message!!.endsWith("test"))
    }

    @Test
    fun isDebug() {
        var _event: CSLoggerEvent? = null
        var _message: String? = null
        val listener = { event: CSLoggerEvent, message: String ->
            _event = event
            _message = message
        }
        init(CSPrintLogger(name = "TestLog", isDebug = false, listener))
        logDebug { "test" }
        Assert.assertNull(_event)

        init(CSPrintLogger(name = "TestLog", isDebug = true, listener))
        logDebug { "test2" }
        Assert.assertEquals(Debug, _event)
        Assert.assertTrue(_message!!.endsWith("test2"))
    }
}