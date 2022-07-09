package renetik.android.core.logging

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import renetik.android.core.logging.CSLog.init
import renetik.android.core.logging.CSLog.logDebug
import renetik.android.core.logging.CSLog.logWarn
import renetik.android.core.logging.CSLoggerEvent.Debug
import renetik.android.core.logging.CSLoggerEvent.Warn

@RunWith(RobolectricTestRunner::class)
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

        assertEquals(Warn, event)
        val messageEnd =
            "renetik.android.core.logging.CSAndroidLoggerTest\$logWithListener(CSAndroidLoggerTest.kt:26) test"
        assertTrue(message!!.endsWith(messageEnd))
    }

    @Test
    fun isDebug() {
        init(CSAndroidLogger(name = "TestLog", isDebug = false, listener))
        logDebug { "test" }
        assertNull(event)
        assertNull(message)

        init(CSAndroidLogger(name = "TestLog", isDebug = true, listener))
        logDebug { "test2" }
        assertEquals(Debug, event)
        val messageEnd =
            "renetik.android.core.logging.CSAndroidLoggerTest\$isDebug(CSAndroidLoggerTest.kt:42) test2"
        assertTrue(message!!.endsWith(messageEnd))
    }
}