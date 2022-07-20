package renetik.android.core.logging

import org.junit.Assert
import org.junit.Test
import renetik.android.core.logging.CSLog.init
import renetik.android.core.logging.CSLog.logDebug
import renetik.android.core.logging.CSLog.logWarn
import renetik.android.core.logging.CSLogMessage.Companion.message
import renetik.android.core.logging.CSLogLevel.*

class CSPrintLoggerTest {
    @Test
    fun warnTestNoInit() {
        logWarn { message("test") }
    }

    @Test
    fun logWithListener() {
        var _event: CSLogLevel? = null
        var _message: String? = null
        init(CSPrintLogger(name = "TestLog") { event, message ->
            _event = event
            _message = message
        })
        logWarn { message("test") }
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
        logDebug { message("test") }
        Assert.assertNull(_event)

        init(CSPrintLogger(name = "TestLog", level = Debug, listener))
        logDebug { message("test2") }
        Assert.assertEquals(Debug, _event)
        Assert.assertTrue(_message!!.endsWith("test2 "))
    }
}