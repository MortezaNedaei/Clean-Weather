package com.mooncascade.presentation.base

import androidx.lifecycle.ViewModel
import com.mooncascade.domain.model.ViewState
import com.mooncascade.presentation.utils.Constants
import java.io.IOException
import javax.inject.Inject

open class BaseViewModel @Inject constructor() : ViewModel() {

    protected fun <T> makeError(e: Throwable?, TAG: String, message: String): ViewState.Error<T> {
        return if (e is IOException) {
            ViewState.Error(
                Constants.ERROR_NETWORK, null, true
            )
        } else {
            ViewState.Error(
                e?.message ?: "$TAG: $message"
            )
        }
    }
}