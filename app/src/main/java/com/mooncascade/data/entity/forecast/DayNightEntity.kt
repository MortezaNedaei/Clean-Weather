package com.mooncascade.data.entity.forecast


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DayNightEntity(
    @SerializedName("peipsi")
    val peipsi: String?,
    @SerializedName("phenomenon")
    val phenomenon: String?,
    @SerializedName("places")
    val places: List<PlaceEntity>?,
    @SerializedName("sea")
    val sea: String?,
    @SerializedName("tempmax")
    val tempmax: Int?,
    @SerializedName("tempmin")
    val tempmin: Int?,
    @SerializedName("text")
    val text: String?,
    @SerializedName("winds")
    val winds: List<WindEntity>?
)