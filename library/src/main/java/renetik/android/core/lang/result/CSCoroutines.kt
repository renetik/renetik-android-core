package renetik.android.core.lang.result

import kotlinx.coroutines.delay
import renetik.android.core.java.lang.nowDuration
import java.util.concurrent.TimeoutException
import kotlin.time.Duration

object CSCoroutines {
    suspend fun waitFor(
        timeout: Duration,
        message: String = "Condition not met within $timeout",
        condition: () -> Boolean
    ) {
        val startTime = nowDuration
        while (!condition()) {
            if (nowDuration - startTime > timeout)
                throw TimeoutException(message)
            delay(50)
        }
    }
}