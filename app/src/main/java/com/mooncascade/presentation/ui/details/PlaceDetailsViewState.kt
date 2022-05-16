package com.mooncascade.presentation.ui.details

import com.mooncascade.data.entity.forecast.PlaceEntity

data class PlaceDetailsViewState(
    val place: PlaceEntity? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null
)