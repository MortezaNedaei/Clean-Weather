package com.mooncascade.domain.model

import com.mooncascade.domain.model.ViewState.Status.*


/**
 * a utility sealed class that communicates the current state of Network Call to the UI Layer.
 */
sealed class ViewState<out T>(
    val data: T? = null,
    val message: String? = null,
    val status: Status,
    val serverError: Boolean? = false
) {
    class Success<T>(data: T?) : ViewState<T>(data, null, SUCCESS)
    class Error<T>(message: String, data: T? = null, serverError: Boolean? = false) :
        ViewState<T>(data, message, ERROR)

    object Loading : ViewState<Nothing>(status = LOADING)
    object Idle : ViewState<Nothing>(status = IDLE)

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
        IDLE
    }
}
