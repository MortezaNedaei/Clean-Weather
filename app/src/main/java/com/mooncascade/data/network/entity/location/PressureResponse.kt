package com.mooncascade.data.network.entity.location


import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class PressureResponse(
    @SerialName("units")
    val units: String?,
    @SerialName("value")
    val value: String?
)