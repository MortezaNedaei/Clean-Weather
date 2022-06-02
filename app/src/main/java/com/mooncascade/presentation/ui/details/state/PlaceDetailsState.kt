package com.mooncascade.presentation.ui.details.state

import com.mooncascade.domain.model.current.Observation
import com.mooncascade.domain.model.location.Location


/**
 * PlaceDetails UI State
 */
data class PlaceDetailsState(
    val isLoading: Boolean = false,
    val observation: Observation? = null, // extra arg from navigation
    val location: Location? = null, // new network response data
    val error: String? = null,
)