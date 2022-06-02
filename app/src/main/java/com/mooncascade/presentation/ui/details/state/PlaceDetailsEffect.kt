package com.mooncascade.presentation.ui.details.state

/**
 * The actions that shouldn't be re-emitted whenever the view is recreated due to a configuration change
 * (f.e. showing a snackBar, dialog or navigation action)
 */
sealed interface PlaceDetailsEffect {
    data class ShowError(val message: String) : PlaceDetailsEffect
}