package com.mooncascade.presentation.base

import androidx.lifecycle.ViewModel
import com.mooncascade.domain.model.DataState
import com.mooncascade.presentation.utils.Constants
import java.io.IOException
import javax.inject.Inject

open class BaseViewModel @Inject constructor() : ViewModel() {

    protected fun <T> makeError(e: Throwable?, TAG: String, message: String): DataState.Error<T> {
        return if (e is IOException) {
            DataState.Error(
                Constants.ERROR_NETWORK, null, true
            )
        } else {
            DataState.Error(
                e?.message ?: "$TAG: $message"
            )
        }
    }
}