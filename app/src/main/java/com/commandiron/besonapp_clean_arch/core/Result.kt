package com.commandiron.besonapp_clean_arch.core

sealed class Result<T>(
    val data: T? = null,
    val exception: Exception? = null
) {

    class Success<T>(data: T) : Result<T>(data)

    class Error<T>(e: Exception?, data: T? = null) : Result<T>(data, e)

    class Loading<T> : Result<T>()

}