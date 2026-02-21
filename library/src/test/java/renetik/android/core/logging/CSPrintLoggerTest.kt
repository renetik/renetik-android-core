package renetik.android.core.logging

import org.junit.Assert
import org.junit.Test
import renetik.android.core.logging.CSLog.init
import renetik.android.core.logging.CSLog.logDebug
import renetik.android.core.logging.CSLog.logWarn
import renetik.android.core.logging.CSLogLevel.Debug
import renetik.android.core.logging.CSLogLevel.Info
import renetik.android.core.logging.CSLogLevel.Warn

class CSPrintLoggerTest {
    @Test
    fun warnTestNoInit() {
        logWarn { "test" }
    }

    @Test
    fun logWithListener() {
        var _event: CSLogLevel? = null
        var _message: String? = null
        init(CSPrintLogger(name = "TestLog") { event, message ->
            _event = event
            _message = message
        })
        logWarn { "test" }
        Assert.assertEquals(Warn, _event)
        Assert.assertTrue(_message!!.endsWith("test "))
    }

    @Test
    fun isDebug() {
        var _event: CSLogLevel? = null
        var _message: String? = null
        val listener = { event: CSLogLevel, message: String ->
            _event = event
            _message = message
        }
        init(CSPrintLogger(name = "TestLog", level = Info, listener))
        logDebug { "test" }
        Assert.assertNull(_event)

        init(CSPrintLogger(name = "TestLog", level = Debug, listener))
        logDebug { "test2" }
        Assert.assertEquals(Debug, _event)
        Assert.assertTrue(_message!!.endsWith("test2 "))
    }
}