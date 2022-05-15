package com.mooncascade.data.entity


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ForecastDataEntity(
    @SerializedName("forecasts")
    val forecasts: List<ForecastEntity>?,
)