package renetik.android.core.java.lang

import renetik.android.core.lang.catchAllWarnReturnNull

fun <T> Class<T>.createInstance(): T? = catchAllWarnReturnNull { this.newInstance() }