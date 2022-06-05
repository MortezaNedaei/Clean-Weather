package com.mooncascade.domain.model.forecast


import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Forecast(
    val date: String?,
    val day: DayNight?,
    val night: DayNight?
)