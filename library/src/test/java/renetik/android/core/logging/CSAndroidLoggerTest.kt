package renetik.android.core.logging

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import renetik.android.core.logging.CSLog.init
import renetik.android.core.logging.CSLog.logDebug
import renetik.android.core.logging.CSLog.logWarn
import renetik.android.core.logging.CSLoggerEvent.Debug
import renetik.android.core.logging.CSLoggerEvent.Warn

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class CSAndroidLoggerTest {
    var event: CSLoggerEvent? = null
    var message: String? = null
    private val listener = { event: CSLoggerEvent, message: String ->
        this.event = event
        this.message = message
    }

    @Test
    fun logWithListener() {
        init(CSAndroidLogger(name = "TestLog", isDebug = true, listener))
        logWarn("test")
        Assert.assertEquals(Warn, event)
        Assert.assertTrue(message!!.endsWith("test"))
    }

    @Test
    fun isDebug() {
        init(CSAndroidLogger(name = "TestLog", isDebug = false, listener))
        logDebug { "test" }
        Assert.assertNull(event)

        init(CSAndroidLogger(name = "TestLog", isDebug = true, listener))
        logDebug { "test2" }
        Assert.assertEquals(Debug, event)
        Assert.assertTrue(message!!.endsWith("test2"))
    }
}