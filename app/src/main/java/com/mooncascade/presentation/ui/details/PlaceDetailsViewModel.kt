package com.mooncascade.presentation.ui.details

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mooncascade.R
import com.mooncascade.common.extensions.convertNumberToWords
import com.mooncascade.data.di.qualifier.IoDispatcher
import com.mooncascade.data.entity.current.ObservationEntity
import com.mooncascade.data.entity.location.LocationEntity
import com.mooncascade.data.respository.WeatherDataRepository
import com.mooncascade.domain.interactor.GetLocationWeatherUseCase
import com.mooncascade.domain.interactor.LocationWeatherParams
import com.mooncascade.domain.model.ViewState
import com.mooncascade.domain.model.WeatherType
import com.mooncascade.presentation.utils.Constants
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class PlaceDetailsViewModel @AssistedInject constructor(
    private val repository: WeatherDataRepository,
    private val useCase: GetLocationWeatherUseCase,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    @ActivityContext private val context: Context,
    @Assisted private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    companion object {
        const val TAG = "PlaceDetailsViewModel"
    }

    @AssistedFactory
    interface PlaceDetailsViewModelFactory {
        fun create(handle: SavedStateHandle): PlaceDetailsViewModel
    }

    private val argument by lazy(LazyThreadSafetyMode.NONE) {
        savedStateHandle.get<ObservationEntity>("item")
    }

    private val _locationWeatherFlow =
        MutableStateFlow<ViewState<LocationEntity>>(ViewState.Idle)
    val locationWeatherFlow get() = _locationWeatherFlow

    init {
        getLocationWeather(argument?.wmocode)
    }

    private fun getLocationWeather(locationId: String?) =
        viewModelScope.launch(coroutineDispatcher) {

            // We can't make a request when there is no id for this place
            // TODO: add a ui test to check this
            if (locationId.isNullOrEmpty()) return@launch
            _locationWeatherFlow.value = ViewState.Loading
            useCase.invoke(LocationWeatherParams(locationId.toInt()))
                .catch { e ->
                    _locationWeatherFlow.value =
                        ViewState.Error(
                            e.message ?: "$TAG: ${Constants.ERROR_GET_LOCATION_FORECAST}"
                        )
                }
                .collect { data ->
                    _locationWeatherFlow.value = ViewState.Success(data.getOrNull())
                }
        }

    fun getCurrentWeatherAnimation(phenomenon: String?): Int {
        val weatherAnim = WeatherType.values().find { weatherType ->
            weatherType.weatherDescription.contains(phenomenon!!)
        }?.weatherAnim

        return weatherAnim ?: WeatherType.UNKNOWN.weatherAnim
    }

    fun getLocationWeatherDegrees(
        temperature: String?,
        showFullDegrees: Boolean = false
    ): CharSequence {
        if (temperature.isNullOrEmpty()) {
            return "- -"
        }
        return if (showFullDegrees) {
            context.resources.getString(
                R.string.format_temp_full_degrees,
                temperature,
                temperature.toFloat().toInt().convertNumberToWords()
            )
        } else {
            context.resources.getString(R.string.format_temp, temperature)
        }
    }

}