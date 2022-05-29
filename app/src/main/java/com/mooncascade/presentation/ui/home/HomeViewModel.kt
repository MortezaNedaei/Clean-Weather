package com.mooncascade.presentation.ui.home

import androidx.lifecycle.viewModelScope
import com.mooncascade.di.qualifier.IoDispatcher
import com.mooncascade.domain.interactor.GetNextDaysForecastsUseCase
import com.mooncascade.domain.interactor.GetObservationsUseCase
import com.mooncascade.domain.model.DataState
import com.mooncascade.domain.model.WeatherType
import com.mooncascade.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observationsUseCase: GetObservationsUseCase,
    private val nextDaysForecastsUseCase: GetNextDaysForecastsUseCase,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState get() = _uiState.asStateFlow()

    init {
        getObservations()
        getNextDaysForecasts()
    }

    private fun getObservations() = viewModelScope.launch(coroutineDispatcher) {
        observationsUseCase.invoke()
            .collect { data ->
                when (data) {
                    is DataState.Loading -> _uiState.update {
                        it.copy(
                            observationsUiState = it.observationsUiState.copy(
                                isLoading = true
                            )
                        )
                    }
                    is DataState.Success -> {
                        _uiState.update {
                            it.copy(
                                observationsUiState = it.observationsUiState.copy(
                                    observations = data.data,
                                    isLoading = false
                                ),
                            )
                        }
                    }
                    is DataState.Error -> {
                        _uiState.update {
                            it.copy(
                                observationsUiState = it.observationsUiState.copy(isLoading = false),
                                error = data.message,
                            )
                        }
                    }
                    else -> {}
                }
            }
    }


    private fun getNextDaysForecasts() = viewModelScope.launch(coroutineDispatcher) {
        nextDaysForecastsUseCase.invoke()
            .collect { data ->
                when (data) {
                    is DataState.Loading -> _uiState.update {
                        it.copy(
                            forecastsUiState = it.forecastsUiState.copy(
                                isLoading = true
                            )
                        )
                    }
                    is DataState.Success -> {
                        _uiState.update {
                            it.copy(
                                forecastsUiState = it.forecastsUiState.copy(
                                    forecasts = data.data,
                                    isLoading = false
                                ),
                            )
                        }
                    }
                    is DataState.Error -> {
                        _uiState.update {
                            it.copy(
                                forecastsUiState = it.forecastsUiState.copy(isLoading = false),
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

}