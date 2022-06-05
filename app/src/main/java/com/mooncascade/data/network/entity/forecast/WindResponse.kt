package com.mooncascade.data.network.entity.forecast


import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class WindResponse(
    @SerialName("direction")
    val direction: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("speedmax")
    val speedmax: Double?,
    @SerialName("speedmin")
    val speedmin: Double?
)