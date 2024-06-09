package renetik.android.core.lang

import renetik.android.core.lang.CSResult.State.Cancel
import renetik.android.core.lang.CSResult.State.Failure
import renetik.android.core.lang.CSResult.State.Success

data class CSResult<Value>(
    val state: State,
    val value: Value?,
    var throwable: Throwable? = null,
    var message: String? = null
) {
    enum class State { Success, Cancel, Failure; }

    val isSuccess get() = state == Success

    suspend fun ifSuccess(function: suspend (Value) -> Unit) = apply {
        if (state == Success) runCatching { function(value!!) }
            .onFailure { throwable = it }
    }

    @JvmName("onSuccessValueToResult")
    suspend fun <T> ifSuccess(function: suspend (Value) -> CSResult<T>): CSResult<T> =
        if (state == Success) {
            runCatching { function(value!!) }.getOrNull()
                ?: CSResult(state, null, throwable, message)
        } else CSResult(state, null, throwable, message)

    suspend fun ifNotSuccess(function: suspend () -> Unit) = apply {
        if (state == Failure || state == Cancel) function()
    }

//    suspend fun ifFailure(function: suspend (Pair<Throwable?, String?>) -> Unit) = apply {
//        if (state == Failure) function(throwable to message)
//    }

    suspend fun ifFailure(function: suspend (CSResult<Value>) -> Unit) = apply {
        if (state == Failure) function(this)
    }

    suspend fun ifCancel(function: suspend () -> Unit) = apply {
        if (state == Cancel) function()
    }

    companion object {
        val success: CSResult<Unit> = CSResult(Success, Unit)

        fun <Value> success(value: Value) = CSResult(Success, value)

        val cancel: CSResult<Unit> = CSResult(Cancel, Unit)

        fun <Value> cancel() = CSResult<Value>(Cancel, null)

        fun <Value> failure(throwable: Throwable? = null, message: String? = null) =
            CSResult<Value>(Failure, value = null, throwable = throwable, message = message)

        fun <Value> failure(message: String): CSResult<Value> =
            CSResult(Failure, value = null, message = message)
    }
}