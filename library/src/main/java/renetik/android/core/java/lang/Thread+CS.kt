package renetik.android.core.java.lang

import android.os.Looper.getMainLooper
import java.lang.Thread.currentThread

val Thread.isMain get() = getMainLooper().thread == this

val isThreadMain get() = getMainLooper().thread == currentThread()
