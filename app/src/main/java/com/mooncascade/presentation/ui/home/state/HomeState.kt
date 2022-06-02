package com.mooncascade.presentation.ui.home.state

import com.mooncascade.domain.model.current.Observation
import com.mooncascade.domain.model.forecast.Forecast

/**
 * Home UI state
 */
data class HomeState(
    // This states should not be null. because we use `copy()` command to update the state
    val observationsState: ObservationsState = ObservationsState(),
    val forecastsState: ForecastsState = ForecastsState(),
    val error: String? = null,
)

data class ObservationsState(
    val isLoading: Boolean = false,
    val observations: List<Observation>? = null,
)

data class ForecastsState(
    val isLoading: Boolean = false,
    val forecasts: List<Forecast>? = null,
)