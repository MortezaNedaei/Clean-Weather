package com.mooncascade.presentation.ui.home

import androidx.lifecycle.viewModelScope
import com.mooncascade.di.qualifier.IoDispatcher
import com.mooncascade.domain.interactor.GetNextDaysForecastsUseCase
import com.mooncascade.domain.interactor.GetObservationsUseCase
import com.mooncascade.domain.model.ViewState
import com.mooncascade.domain.model.WeatherType
import com.mooncascade.domain.model.current.Observation
import com.mooncascade.domain.model.forecast.Forecast
import com.mooncascade.presentation.base.BaseViewModel
import com.mooncascade.presentation.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observationsUseCase: GetObservationsUseCase,
    private val nextDaysForecastsUseCase: GetNextDaysForecastsUseCase,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    companion object {
        private const val TAG = "HomeViewModel"
    }

    private val _observationsFlow =
        MutableStateFlow<ViewState<List<Observation>>>(ViewState.Idle)
    val observationsFlow get() = _observationsFlow


    private val _nextDaysForecastsFlow =
        MutableStateFlow<ViewState<List<Forecast>>>(ViewState.Idle)
    val nextDaysForecastsFlow get() = _nextDaysForecastsFlow

    init {
        getObservations()
        getNextDaysForecasts()
    }

    private fun getObservations() = viewModelScope.launch(coroutineDispatcher) {
        _observationsFlow.value = ViewState.Loading
        observationsUseCase.invoke()
            .collect { data ->
                _observationsFlow.value =
                    if (data.isSuccess)
                        ViewState.Success(data.getOrNull())
                    else
                        makeError(
                            data.exceptionOrNull(), TAG, Constants.ERROR_GET_CURRENT_WEATHER
                        )
            }
    }


    private fun getNextDaysForecasts() = viewModelScope.launch(coroutineDispatcher) {
        _nextDaysForecastsFlow.value = ViewState.Loading
        nextDaysForecastsUseCase.invoke()
            .collect { data ->
                _nextDaysForecastsFlow.value =
                    if (data.isSuccess)
                        ViewState.Success(data.getOrNull() ?: emptyList())
                    else
                        makeError(
                            data.exceptionOrNull(), TAG, Constants.ERROR_GET_FORECASTS
                        )
            }
    }


    fun getCurrentWeatherAnimation(phenomenon: String?): Int {
        val weatherAnim = WeatherType.values().find { weatherType ->
            weatherType.weatherDescription.contains(phenomenon!!)
        }?.weatherAnim

        return weatherAnim ?: WeatherType.UNKNOWN.weatherAnim
    }

}