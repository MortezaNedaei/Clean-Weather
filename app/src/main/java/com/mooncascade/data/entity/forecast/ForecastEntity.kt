package com.mooncascade.data.entity.forecast


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ForecastEntity(
    @SerializedName("date")
    val date: String?,
    @SerializedName("day")
    val day: DayNightEntity?,
    @SerializedName("night")
    val night: DayNightEntity?
)