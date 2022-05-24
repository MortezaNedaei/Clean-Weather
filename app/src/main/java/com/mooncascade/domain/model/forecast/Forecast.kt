package com.mooncascade.domain.model.forecast


import androidx.annotation.Keep

@Keep
data class Forecast(
    val date: String?,
    val day: DayNight?,
    val night: DayNight?
)