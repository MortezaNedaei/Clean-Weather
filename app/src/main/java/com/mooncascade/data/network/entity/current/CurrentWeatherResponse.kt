package com.mooncascade.data.network.entity.current

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class CurrentWeatherResponse(
    @SerialName("timestamp")
    val timestamp: String,
    @SerialName("observations")
    val observations: List<ObservationResponse>,
)
