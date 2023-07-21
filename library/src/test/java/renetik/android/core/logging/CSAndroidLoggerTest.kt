package renetik.android.core.logging

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import renetik.android.core.kotlin.primitives.leaveEndOfLength
import renetik.android.core.logging.CSLog.init
import renetik.android.core.logging.CSLog.logDebug
import renetik.android.core.logging.CSLog.logInfo
import renetik.android.core.logging.CSLog.logWarn
import renetik.android.core.logging.CSLogLevel.*

@RunWith(RobolectricTestRunner::class)
class CSAndroidLoggerTest {

    var event: CSLogLevel? = null
    var message: String? = null
    private val listener = { event: CSLogLevel, message: String ->
        this.event = event
        this.message = message
    }

    @Test
    fun logWithListener() {
        init(CSAndroidLogger(tag = "TestLog", level = Debug, listener))
        logWarn { "test" }
        assertEquals(Warn, event)
        val messageEnd =
            "renetik.android.core.logging.CSAndroidLoggerTest\$logWithListener(CSAndroidLoggerTest.kt:27) test"
        assertTrue(message!!.endsWith(messageEnd))
    }

    @Test
    fun logEmpty() {
        init(CSAndroidLogger(tag = "TestLog", level = Debug, listener))
        logInfo()
        assertEquals(Info, event)
        val messageEnd =
            "renetik.android.core.logging.CSAndroidLoggerTest\$logEmpty(CSAndroidLoggerTest.kt:37)"
        assertEquals(messageEnd, message?.leaveEndOfLength(messageEnd.length))
    }

    @Test
    fun isDebug() {
        init(CSAndroidLogger(tag = "TestLog", level = Info, listener))
        logDebug { "test" }
        assertNull(event)
        assertNull(message)

        init(CSAndroidLogger(tag = "TestLog", level = Debug, listener))
        logDebug { "test2" }
        assertEquals(Debug, event)
        val messageEnd =
            "renetik.android.core.logging.CSAndroidLoggerTest\$isDebug(CSAndroidLoggerTest.kt:52) test2"
        assertTrue(message!!.endsWith(messageEnd))
    }
}