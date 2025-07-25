package renetik.android.core.java.lang

import android.os.Looper
import java.lang.Thread.currentThread

private val mainThread: Thread = Looper.getMainLooper().thread
val Thread.isMain get() = mainThread == this
val isThreadMain get() = mainThread == currentThread()
