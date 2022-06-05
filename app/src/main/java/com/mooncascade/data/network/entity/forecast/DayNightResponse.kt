package com.mooncascade.data.network.entity.forecast


import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class DayNightResponse(
    @SerialName("peipsi")
    val peipsi: String?,
    @SerialName("phenomenon")
    val phenomenon: String?,
    @SerialName("places")
    val places: List<PlaceResponse>?,
    @SerialName("sea")
    val sea: String?,
    @SerialName("tempmax")
    val tempmax: Double?,
    @SerialName("tempmin")
    val tempmin: Double?,
    @SerialName("text")
    val text: String?,
    @SerialName("winds")
    val winds: List<WindResponse>?
)