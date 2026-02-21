package renetik.android.core.logging

import org.junit.Assert
import org.junit.Test
import renetik.android.core.logging.CSLog.init
import renetik.android.core.logging.CSLog.logDebug
import renetik.android.core.logging.CSLog.logWarn
import renetik.android.core.logging.CSLogLevel.*

class CSDummyLoggerTest {
    var event: CSLogLevel? = null
    var message: String? = null

    @Test
    fun warnTestNoInit() {
        logWarn { "test" }
    }

    @Test
    fun logWithListener() {
        init(CSDummyLogger { event, message ->
            this.event = event
            this.message = message
        })
        logWarn { "test" }
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
        logDebug { "test" }
        Assert.assertNull(event)

        init(CSDummyLogger(level = Debug, listener))
        logDebug { "test2" }
        Assert.assertEquals(Debug, event)
//        Assert.assertTrue(message!!.endsWith("test2"))
    }
}