package com.mooncascade.data.network.entity.forecast


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ForecastResponse(
    @SerializedName("date")
    val date: String?,
    @SerializedName("day")
    val day: DayNightResponse?,
    @SerializedName("night")
    val night: DayNightResponse?
)