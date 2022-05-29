package com.mooncascade.presentation.ui.home

import com.mooncascade.domain.model.current.Observation
import com.mooncascade.domain.model.forecast.Forecast

/**
 * Home UI state
 */
data class HomeUiState(
    // This states should not be null. because we use `copy()` command to update the state
    val observationsUiState: ObservationsUiState = ObservationsUiState(),
    val forecastsUiState: ForecastsUiState = ForecastsUiState(),
    val error: String? = null,
)

data class ObservationsUiState(
    val isLoading: Boolean = false,
    val observations: List<Observation>? = null,
)

data class ForecastsUiState(
    val isLoading: Boolean = false,
    val forecasts: List<Forecast>? = null,
)