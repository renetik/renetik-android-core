package renetik.android.core.lang.result

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.newSingleThreadContext

@OptIn(ExperimentalStdlibApi::class)
suspend fun currentDispatcher(): CoroutineDispatcher? =
    currentCoroutineContext()[CoroutineDispatcher]

@OptIn(DelicateCoroutinesApi::class, ExperimentalCoroutinesApi::class)
val SingleThread: ExecutorCoroutineDispatcher = newSingleThreadContext("SingleThread")
