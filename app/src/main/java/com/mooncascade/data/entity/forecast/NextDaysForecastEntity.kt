package com.mooncascade.data.entity.forecast


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class NextDaysForecastEntity(
    @SerializedName("forecasts")
    val forecasts: List<ForecastEntity>?,
)