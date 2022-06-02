package com.mooncascade.presentation.ui.home.state

/**
 * used as old Intent: user actions, intention or desire to perform an action (e.g. get a data, clicking a button or typing text)
 */
sealed interface HomeEvent {
    object GetObservations : HomeEvent
    object GetNextDaysForecasts : HomeEvent
    object Refresh : HomeEvent
}
