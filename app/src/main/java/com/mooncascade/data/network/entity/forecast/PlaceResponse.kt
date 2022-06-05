package com.mooncascade.data.network.entity.forecast


import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class PlaceResponse(
    @SerialName("name")
    val name: String?,
    @SerialName("phenomenon")
    val phenomenon: String?,
    @SerialName("tempmax")
    val tempmax: Double?,
    @SerialName("tempmin")
    val tempmin: Double?
)