package renetik.android.core.lang.result

import kotlinx.coroutines.CoroutineDispatcher
import renetik.android.core.lang.result.CSResult.State.Cancel
import renetik.android.core.lang.result.CSResult.State.Failure
import renetik.android.core.lang.result.CSResult.State.Success

data class CSResult<Value>(
    val state: State,
    val value: Value? = null,
    var throwable: Throwable? = null,
    val message: String? = null,
    val code: Int? = null,
) {
    enum class State { Success, Cancel, Failure; }

    val isSuccess get() = state == Success
    val isFailure get() = state == Failure
    val isCancel get() = state == Cancel

    suspend inline fun ifSuccess(
        crossinline function: suspend (Value) -> Unit
    ): CSResult<Value> = ifSuccess(EmptyDispatcher, function)

    suspend inline fun ifSuccess(
        dispatcher: CoroutineDispatcher,
        crossinline function: suspend (Value) -> Unit
    ): CSResult<Value> =
        if (isSuccess) runCatching { dispatcher { function(value!!); this } }
            .getOrElse { failure(it) }
        else this

    suspend inline fun <T> ifSuccessReturn(
        crossinline function: suspend (Value) -> CSResult<T>
    ): CSResult<T> = ifSuccessReturn(EmptyDispatcher, function)

    suspend inline fun <T> ifSuccessReturn(
        dispatcher: CoroutineDispatcher,
        crossinline function: suspend (Value) -> CSResult<T>
    ): CSResult<T> =
        if (isSuccess) runCatching { dispatcher { function(value!!) } }
            .getOrElse { failure(it) }
        else CSResult(state, null, throwable, message, code)

    suspend inline fun ifNotSuccess(
        crossinline function: suspend () -> Unit
    ): CSResult<Value> = ifNotSuccess(EmptyDispatcher, function)

    suspend inline fun ifNotSuccess(
        dispatcher: CoroutineDispatcher,
        crossinline function: suspend () -> Unit
    ): CSResult<Value> = apply {
        if (isFailure || isCancel) dispatcher { function() }
    }

    suspend inline fun ifFailure(
        crossinline function: suspend (CSResult<Value>) -> Unit
    ): CSResult<Value> = ifFailure(EmptyDispatcher, function)

    suspend inline fun ifFailure(
        dispatcher: CoroutineDispatcher,
        crossinline function: suspend (CSResult<Value>) -> Unit
    ): CSResult<Value> = apply {
        if (isFailure) dispatcher { function(this) }
    }

    suspend inline fun ifCancel(
        crossinline function: suspend () -> Unit
    ) = ifCancel(EmptyDispatcher, function)

    suspend inline fun ifCancel(
        dispatcher: CoroutineDispatcher,
        crossinline function: suspend () -> Unit
    ) = apply {
        if (isCancel) dispatcher { function() }
    }

    companion object {
        val success: CSResult<Unit> = CSResult(Success, Unit)
        val failure: CSResult<Unit> = CSResult(Failure, Unit)
        val cancel: CSResult<Unit> = CSResult(Cancel, Unit)

        fun <Value> success(value: Value) = CSResult(Success, value)
        fun <Value> cancel() = CSResult<Value>(Cancel)

        fun <Value> failure(throwable: Throwable? = null, message: String? = null) =
            CSResult<Value>(Failure, throwable = throwable, message = message)

        fun <Value> failure(message: String): CSResult<Value> =
            CSResult(Failure, message = message)

        fun <Value> failure(code: Int, message: String? = null): CSResult<Value> =
            CSResult(Failure, code = code, message = message)
    }
}