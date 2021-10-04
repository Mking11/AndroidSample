package com.mking11.androidDemo.common.models


sealed class AppResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : AppResult<T>()
    object InProgress : AppResult<Nothing>()
    data class Failure<out T : Any>(
        val failure: T?,
        val messages: String,
        val errors: HashMap<String, String>? = null,
    ) : AppResult<T>()

    sealed class Error(val exception: Exception?) : AppResult<Nothing>() {
        class RecoverableError(exception: Exception) : Error(exception)
        class NonRecoverableError(exception: Exception) : Error(exception)
    }
}




