package renetik.android.core.java.util.concurrent

import android.os.Process.setThreadPriority
import renetik.android.core.lang.catchAllWarn
import java.lang.Thread.MAX_PRIORITY
import java.util.concurrent.ThreadFactory

class MaxPriorityThreadFactory : ThreadFactory {
    override fun newThread(runnable: Runnable) = Thread {
        catchAllWarn { setThreadPriority(-20) }
        runnable.run()
    }.also { it.priority = MAX_PRIORITY }
}