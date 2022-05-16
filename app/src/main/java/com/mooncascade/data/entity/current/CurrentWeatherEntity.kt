package com.mooncascade.data.entity.current

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CurrentWeatherEntity(
    @SerializedName("timestamp")
    val timestamp: String,
    @SerializedName("observations")
    val observations: List<ObservationEntity>,
)
