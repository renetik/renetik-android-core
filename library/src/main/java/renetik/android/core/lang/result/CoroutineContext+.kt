package renetik.android.core.lang.result

import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

suspend fun <T> CoroutineContext.context(
    block: suspend () -> T
): T = withContext(this) { block() }