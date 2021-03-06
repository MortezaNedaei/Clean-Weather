package com.mooncascade.presentation.ui.details

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mooncascade.R
import com.mooncascade.common.extensions.convertNumberToWords
import com.mooncascade.di.qualifier.IoDispatcher
import com.mooncascade.domain.interactor.GetLocationDetailsUseCase
import com.mooncascade.domain.interactor.LocationWeatherParams
import com.mooncascade.domain.model.DataState
import com.mooncascade.domain.model.WeatherType
import com.mooncascade.domain.model.current.Observation
import com.mooncascade.presentation.base.BaseViewModel
import com.mooncascade.presentation.ui.details.state.PlaceDetailsEffect
import com.mooncascade.presentation.ui.details.state.PlaceDetailsEvent
import com.mooncascade.presentation.ui.details.state.PlaceDetailsState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class PlaceDetailsViewModel @AssistedInject constructor(
    private val useCase: GetLocationDetailsUseCase,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    @ActivityContext private val context: Context,
    @Assisted private val savedStateHandle: SavedStateHandle,
) : BaseViewModel<PlaceDetailsEvent, PlaceDetailsState, PlaceDetailsEffect>() {


    @AssistedFactory
    interface PlaceDetailsViewModelFactory {
        fun create(handle: SavedStateHandle): PlaceDetailsViewModel
    }

    private val argument by lazy(LazyThreadSafetyMode.NONE) {
        savedStateHandle.get<Observation>("item")
    }


    init {
        processEvent(eventChannel)
        viewModelScope.launch(coroutineDispatcher) {
            onEvent(PlaceDetailsEvent.GetExtraLocation)
            onEvent(PlaceDetailsEvent.GetLocationDetails(argument?.wmocode))
        }
    }

    override fun processEvent(eventChannel: Flow<PlaceDetailsEvent>) {
        viewModelScope.launch(coroutineDispatcher) {
            eventChannel.collect { event ->
                when (event) {
                    is PlaceDetailsEvent.GetExtraLocation -> getExtraLocationData()
                    is PlaceDetailsEvent.GetLocationDetails -> getLocationWeather(argument?.wmocode)
                    is PlaceDetailsEvent.Refresh -> refresh()
                }
            }
        }
    }

    private fun refresh() {
        getLocationWeather(argument?.wmocode)
    }

    private fun getExtraLocationData() = viewModelScope.launch(coroutineDispatcher) {
        _viewState.update {
            it.copy(
                isLoading = false,
                observation = argument
            )
        }
    }

    private fun getLocationWeather(locationId: String?) =
        viewModelScope.launch(coroutineDispatcher) {

            // We can't make a request when there is no id for this place
            // TODO: add a ui test to check this
            if (locationId.isNullOrEmpty()) return@launch
            useCase.invoke(LocationWeatherParams(locationId.toInt()))
                .collect { data ->
                    when (data) {
                        is DataState.Loading -> _viewState.update { it.copy(isLoading = true) }
                        is DataState.Success -> {
                            _viewState.update {
                                it.copy(
                                    isLoading = false,
                                    location = data.data,
                                )
                            }
                        }
                        is DataState.Error -> {
                            _viewState.update {
                                it.copy(
                                    isLoading = false,
                                    error = data.message,
                                )
                            }
                        }
                        else -> {}
                    }
                }
        }

    fun getCurrentWeatherAnimation(phenomenon: String?): Int {
        val weatherAnim = WeatherType.values().find { weatherType ->
            weatherType.weatherDescription.contains(phenomenon!!)
        }?.weatherAnim

        return weatherAnim ?: WeatherType.UNKNOWN.weatherAnim
    }

    fun getLocationWeatherDegrees(
        temperature: String?,
        showFullDegrees: Boolean = false
    ): CharSequence {
        if (temperature.isNullOrEmpty()) {
            return "- -"
        }
        return if (showFullDegrees) {
            context.resources.getString(
                R.string.format_temp_full_degrees,
                temperature,
                temperature.toFloat().toInt().convertNumberToWords()
            )
        } else {
            context.resources.getString(R.string.format_temp, temperature)
        }
    }

    override fun initViewState(): PlaceDetailsState = PlaceDetailsState()

}