package renetik.android.core.lang.result

import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

suspend operator fun <T> CoroutineContext.invoke(
    block: suspend () -> T
): T = withContext(this) { block() }