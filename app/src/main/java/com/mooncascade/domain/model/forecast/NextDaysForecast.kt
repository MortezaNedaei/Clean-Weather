package com.mooncascade.domain.model.forecast


import androidx.annotation.Keep

@Keep
data class NextDaysForecast(
    val forecasts: List<Forecast>?,
)