package com.mooncascade.presentation.ui.home

import androidx.lifecycle.viewModelScope
import com.mooncascade.di.qualifier.IoDispatcher
import com.mooncascade.domain.interactor.GetNextDaysForecastsUseCase
import com.mooncascade.domain.interactor.GetObservationsUseCase
import com.mooncascade.domain.model.DataState
import com.mooncascade.domain.model.WeatherType
import com.mooncascade.presentation.base.BaseViewModel
import com.mooncascade.presentation.ui.home.state.HomeEffect
import com.mooncascade.presentation.ui.home.state.HomeEvent
import com.mooncascade.presentation.ui.home.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val observationsUseCase: GetObservationsUseCase,
    private val nextDaysForecastsUseCase: GetNextDaysForecastsUseCase,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
) : BaseViewModel<HomeEvent, HomeState, HomeEffect>() {


    init {
        processEvent(eventChannel)
        viewModelScope.launch(coroutineDispatcher) {
            onEvent(HomeEvent.GetObservations)
            onEvent(HomeEvent.GetNextDaysForecasts)
        }
    }

    override fun processEvent(eventChannel: Flow<HomeEvent>) {
        viewModelScope.launch(coroutineDispatcher) {
            eventChannel.collect { event ->
                when (event) {
                    is HomeEvent.GetObservations -> getObservations()
                    is HomeEvent.GetNextDaysForecasts -> getNextDaysForecasts()
                    is HomeEvent.Refresh -> refresh()
                }
            }
        }
    }

    private fun refresh() {
        getObservations()
        getNextDaysForecasts()
    }


    private fun getObservations() = viewModelScope.launch(coroutineDispatcher) {
        observationsUseCase.invoke()
            .collect { data ->
                when (data) {
                    is DataState.Loading -> _viewState.update {
                        it.copy(
                            observationsState = it.observationsState.copy(
                                isLoading = true
                            )
                        )
                    }
                    is DataState.Success -> {
                        _viewState.update {
                            it.copy(
                                observationsState = it.observationsState.copy(
                                    observations = data.data,
                                    isLoading = false
                                ),
                            )
                        }
                    }
                    is DataState.Error -> {
                        _viewState.update {
                            it.copy(
                                observationsState = it.observationsState.copy(isLoading = false),
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
                    is DataState.Loading -> _viewState.update {
                        it.copy(
                            forecastsState = it.forecastsState.copy(
                                isLoading = true
                            )
                        )
                    }
                    is DataState.Success -> {
                        _viewState.update {
                            it.copy(
                                forecastsState = it.forecastsState.copy(
                                    forecasts = data.data,
                                    isLoading = false
                                ),
                            )
                        }
                    }
                    is DataState.Error -> {
                        _viewState.update {
                            it.copy(
                                forecastsState = it.forecastsState.copy(isLoading = false),
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

    override fun initViewState(): HomeState = HomeState()

}