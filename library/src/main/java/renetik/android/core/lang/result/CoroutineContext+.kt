package renetik.android.core.lang.result

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

suspend fun <T> CoroutineContext.context(
    block: suspend CoroutineScope.() -> T
): T = withContext(this, block)