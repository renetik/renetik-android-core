package renetik.android.core.logging

import org.junit.Assert
import org.junit.Test
import renetik.android.core.logging.CSLog.init
import renetik.android.core.logging.CSLog.logDebug
import renetik.android.core.logging.CSLog.logWarn
import renetik.android.core.logging.CSLogLevel.*
import renetik.android.core.logging.CSLogMessage.Companion.message

class CSDummyLoggerTest {
    var event: CSLogLevel? = null
    var message: String? = null

    @Test
    fun warnTestNoInit() {
        logWarn { message("test") }
    }

    @Test
    fun logWithListener() {
        init(CSDummyLogger { event, message ->
            this.event = event
            this.message = message
        })
        logWarn { message("test") }
        Assert.assertEquals(Warn, event)
//        Assert.assertTrue(message!!.endsWith("test"))
    }

    @Test
    fun isDebug() {
        val listener = { event: CSLogLevel, message: String ->
            this.event = event
            this.message = message
        }
        init(CSDummyLogger(level = Info, listener))
        logDebug { message("test") }
        Assert.assertNull(event)

        init(CSDummyLogger(level = Debug, listener))
        logDebug { message("test2") }
        Assert.assertEquals(Debug, event)
//        Assert.assertTrue(message!!.endsWith("test2"))
    }
}