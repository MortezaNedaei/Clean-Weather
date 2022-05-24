package com.mooncascade.data.network.entity.forecast


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DayNightResponse(
    @SerializedName("peipsi")
    val peipsi: String?,
    @SerializedName("phenomenon")
    val phenomenon: String?,
    @SerializedName("places")
    val places: List<PlaceResponse>?,
    @SerializedName("sea")
    val sea: String?,
    @SerializedName("tempmax")
    val tempmax: Int?,
    @SerializedName("tempmin")
    val tempmin: Int?,
    @SerializedName("text")
    val text: String?,
    @SerializedName("winds")
    val winds: List<WindResponse>?
)