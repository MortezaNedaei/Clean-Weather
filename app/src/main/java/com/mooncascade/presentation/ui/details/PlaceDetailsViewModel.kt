package com.mooncascade.presentation.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mooncascade.data.entity.forecast.ForecastEntity
import com.mooncascade.data.respository.WeatherDataRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class PlaceDetailsViewModel @AssistedInject constructor(
    private val repository: WeatherDataRepository,
    @Assisted private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val argument by lazy {
        savedStateHandle.get<ForecastEntity>("item")
    }

}