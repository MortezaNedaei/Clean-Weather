package com.mooncascade.domain.model.forecast


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class NextDaysForecast(
    val forecasts: List<Forecast>?,
)