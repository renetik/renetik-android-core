package renetik.android.core.java.util.concurrent

import renetik.android.core.logging.CSLog
import renetik.android.core.logging.CSLogMessage
import java.util.concurrent.ThreadPoolExecutor

fun ThreadPoolExecutor.logExecutorInfo() = CSLog.logInfo {
    CSLogMessage.message("completedTaskCount:${completedTaskCount}\n" +
            "taskCount:${taskCount}\n" +
            "corePoolSize:${corePoolSize}\n" +
            "poolSize:${poolSize}\n" +
            "activeCount:${activeCount}")
}