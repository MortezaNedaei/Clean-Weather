package com.mooncascade.data.network.entity.forecast


import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class NextDaysForecastResponse(
    @SerialName("forecasts")
    val forecasts: List<ForecastResponse>?,
)