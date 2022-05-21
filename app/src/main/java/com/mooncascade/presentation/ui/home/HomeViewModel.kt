package com.mooncascade.presentation.ui.home

import androidx.lifecycle.viewModelScope
import com.mooncascade.di.qualifier.IoDispatcher
import com.mooncascade.data.entity.current.CurrentWeatherEntity
import com.mooncascade.data.entity.forecast.ForecastEntity
import com.mooncascade.data.respository.WeatherDataRepository
import com.mooncascade.domain.interactor.GetCurrentWeatherUseCase
import com.mooncascade.domain.interactor.GetNextDaysForecastsUseCase
import com.mooncascade.domain.model.ViewState
import com.mooncascade.domain.model.WeatherType
import com.mooncascade.presentation.base.BaseViewModel
import com.mooncascade.presentation.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WeatherDataRepository,
    private val nextDaysForecastsUseCase: GetNextDaysForecastsUseCase,
    private val currentWeatherUseCase: GetCurrentWeatherUseCase,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    companion object {
        private const val TAG = "HomeViewModel"
    }

    private val _currentWeatherFlow =
        MutableStateFlow<ViewState<CurrentWeatherEntity>>(ViewState.Idle)
    val currentWeatherFlow get() = _currentWeatherFlow

    private val _nextDaysForecastsFlow =
        MutableStateFlow<ViewState<List<ForecastEntity>>>(ViewState.Idle)
    val nextDaysForecastsFlow get() = _nextDaysForecastsFlow

    init {
        getCurrentWeather()
        getNextDaysForecasts()
    }

    private fun getCurrentWeather() = viewModelScope.launch(coroutineDispatcher) {
        _currentWeatherFlow.value = ViewState.Loading
        currentWeatherUseCase.invoke()
            .collect { data ->
                currentWeatherFlow.value =
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
                        ViewState.Success(data.getOrNull()?.forecasts ?: emptyList())
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