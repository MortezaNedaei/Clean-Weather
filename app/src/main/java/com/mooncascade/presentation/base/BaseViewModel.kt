package com.mooncascade.presentation.base

import androidx.lifecycle.ViewModel
import com.mooncascade.domain.model.DataState
import com.mooncascade.presentation.utils.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseViewModel<Event : Any, State : Any, Effect : Any> : ViewModel() {

    protected val _viewState: MutableStateFlow<State> = MutableStateFlow(this.initViewState())
    val viewState get() = _viewState.asStateFlow()

    /**
     * You can change the value of this variable using [onEvent]
     */
    private val _eventChannel = Channel<Event>(Channel.UNLIMITED)
    val eventChannel: Flow<Event> = _eventChannel.receiveAsFlow()

    /**
     * You can change the value of this variable using [onEffect]
     */
    private val _effectChannel = Channel<Effect>(Channel.UNLIMITED)
    val effectChannel: Flow<Effect> = _effectChannel.receiveAsFlow()

    suspend fun onEvent(event: Event) {
        if (!_eventChannel.isClosedForSend)
            _eventChannel.send(event)
    }


    suspend fun onEffect(effect: Effect) {
        if (!_effectChannel.isClosedForSend)
            _effectChannel.send(effect)
    }

    abstract fun initViewState(): State

    abstract fun processEvent(eventChannel: Flow<Event>)


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