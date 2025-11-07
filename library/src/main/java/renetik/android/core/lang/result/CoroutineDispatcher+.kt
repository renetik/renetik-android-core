package renetik.android.core.lang.result

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext

object EmptyDispatcher : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) = block.run()
}

@OptIn(ExperimentalStdlibApi::class)
suspend fun currentDispatcher(): CoroutineDispatcher? =
    currentCoroutineContext()[CoroutineDispatcher]

@OptIn(DelicateCoroutinesApi::class, ExperimentalCoroutinesApi::class)
val SingleThread: ExecutorCoroutineDispatcher = newSingleThreadContext("SingleThread")
