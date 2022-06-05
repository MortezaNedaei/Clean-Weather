package com.mooncascade.data.network.entity.forecast


import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ForecastResponse(
    @SerialName("date")
    val date: String?,
    @SerialName("day")
    val day: DayNightResponse?,
    @SerialName("night")
    val night: DayNightResponse?
)