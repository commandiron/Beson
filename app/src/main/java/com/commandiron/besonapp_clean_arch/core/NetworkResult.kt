package com.commandiron.besonapp_clean_arch.core

sealed class NetworkResult<T>(
    val data: T? = null,
    val exception: Exception? = null
) {

    class Success<T>(data: T) : NetworkResult<T>(data)

    class Error<T>(e: Exception?, data: T? = null) : NetworkResult<T>(data, e)

    class Loading<T> : NetworkResult<T>()

}