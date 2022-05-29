package com.mooncascade.domain.model

import com.mooncascade.domain.model.DataState.Status.*


/**
 * a utility sealed class that communicates the current state of Network Call to the UI Layer.
 */
sealed class DataState<out T>(
    val data: T? = null,
    val message: String? = null,
    val status: Status,
    val networkError: Boolean? = false
) {
    class Success<T>(data: T?) : DataState<T>(data, null, SUCCESS)
    class Error<T>(message: String, data: T? = null, networkError: Boolean? = false) :
        DataState<T>(data, message, ERROR, networkError)

    object Loading : DataState<Nothing>(status = LOADING)
    object Idle : DataState<Nothing>(status = IDLE)

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
        IDLE
    }
}
