package com.mooncascade.data.entity


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ForecastEntity(
    @SerializedName("date")
    val date: String?,
    @SerializedName("day")
    val day: DayEntity?,
    @SerializedName("night")
    val night: NightEntity?
)