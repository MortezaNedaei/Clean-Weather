package com.mooncascade.presentation.ui.details.state


/**
 * used as old Intent: user actions, intention or desire to perform an action (e.g. get a data, clicking a button or typing text)
 */
sealed interface PlaceDetailsEvent {
    object GetExtraLocation : PlaceDetailsEvent
    data class GetLocationDetails(val id: String?) : PlaceDetailsEvent
    object Refresh : PlaceDetailsEvent
}
