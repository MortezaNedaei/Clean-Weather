package com.mooncascade.data.network.entity.forecast


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class NextDaysForecastResponse(
    @SerializedName("forecasts")
    val forecasts: List<ForecastResponse>?,
)