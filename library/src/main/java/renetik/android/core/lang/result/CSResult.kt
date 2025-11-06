package renetik.android.core.lang.result

import kotlinx.coroutines.CoroutineDispatcher
import renetik.android.core.lang.result.CSResult.State.Cancel
import renetik.android.core.lang.result.CSResult.State.Failure
import renetik.android.core.lang.result.CSResult.State.Success

data class CSResult<Value>(
    val state: State,
    val value: Value? = null,
    var throwable: Throwable? = null,
    var message: String? = null,
    var code: Int? = null,
) {
    enum class State { Success, Cancel, Failure; }

    val isSuccess get() = state == Success
    val isFailure get() = state == Failure
    val isCancel get() = state == Cancel

    suspend fun ifSuccess(function: suspend (Value) -> Unit): CSResult<Value> {
        if (isSuccess) {
            val result = runCatching { function(value!!) }
            if (result.isFailure)
                return CSResult(Failure, null, result.exceptionOrNull(), null)
        }
        return this
    }

    suspend fun ifSuccess(
        dispatcher: CoroutineDispatcher,
        function: suspend (Value) -> Unit
    ) = apply {
        if (isSuccess) runCatching {
            dispatcher { function(value!!) }
        }.onFailure { throwable = it }
    }

    suspend fun <T> ifSuccessReturn(function: suspend (Value) -> CSResult<T>): CSResult<T> =
        if (isSuccess) runCatching { function(value!!) }
            .getOrElse { CSResult(Failure, null, it, message) }
        else CSResult(state, null, throwable, message)

    suspend fun ifNotSuccess(function: suspend () -> Unit) = apply {
        if (isFailure || isCancel) function()
    }

    suspend fun ifFailure(function: suspend (CSResult<Value>) -> Unit) = apply {
        if (isFailure) function(this)
    }

    suspend fun ifCancel(function: suspend () -> Unit) = apply {
        if (isCancel) function()
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
            CSResult(Failure, code = code)
    }
}