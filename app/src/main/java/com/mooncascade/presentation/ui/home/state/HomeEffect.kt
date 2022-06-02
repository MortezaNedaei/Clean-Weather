package com.mooncascade.presentation.ui.home.state

import androidx.cardview.widget.CardView
import com.mooncascade.domain.model.current.Observation

/**
 * The actions that shouldn't be re-emitted whenever the view is recreated due to a configuration change
 * (f.e. showing a snackBar, dialog or navigation action)
 */
sealed interface HomeEffect {
    object OnFabPress : HomeEffect
    object OnViewMorePlacesPress : HomeEffect
    data class OnPlaceItemPress(val observation: Observation, val cardView: CardView) : HomeEffect
    data class NavigateToMap(val observation: Observation) : HomeEffect
    data class ShowError(val message: String) : HomeEffect
}