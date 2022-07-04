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

	@Test
	fun logWithListener() {
		var _event: CSLoggerEvent? = null
		var _message: String? = null
		init(CSAndroidLogger(name = "TestLog", isDebug = true) { event, message ->
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
		init(CSAndroidLogger(name = "TestLog", isDebug = false, listener))
		logDebug { "test" }
		Assert.assertNull(_event)

		init(CSAndroidLogger(name = "TestLog", isDebug = true, listener))
		logDebug { "test2" }
		Assert.assertEquals(Debug, _event)
		Assert.assertTrue(_message!!.endsWith("test2"))
	}
}