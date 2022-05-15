package com.mooncascade.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mooncascade.data.di.qualifier.IoDispatcher
import com.mooncascade.data.entity.ForecastEntity
import com.mooncascade.data.respository.WeatherDataRepository
import com.mooncascade.domain.interactor.GetForecastsUseCase
import com.mooncascade.domain.model.ViewState
import com.mooncascade.presentation.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WeatherDataRepository,
    private val useCase: GetForecastsUseCase,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    companion object {
        private const val TAG = "HomeViewModel"
    }

    private val _forecastsFlow = MutableStateFlow<ViewState<List<ForecastEntity>>>(ViewState.Idle)
    val forecastsFlow get() = _forecastsFlow

    init {
        getForecasts()
    }

    private fun getForecasts() = viewModelScope.launch(coroutineDispatcher) {
        _forecastsFlow.value = ViewState.Loading
        useCase.invoke()
            .catch { e ->
                _forecastsFlow.value =
                    ViewState.Error(e.message ?: "$TAG: ${Constants.ERROR_GET_FORECASTS}")
            }
            .collect { data ->
                _forecastsFlow.value = ViewState.Success(data.getOrNull()?.forecasts ?: emptyList())
            }
    }

}