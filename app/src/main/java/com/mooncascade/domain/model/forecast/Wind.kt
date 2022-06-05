package com.mooncascade.domain.model.forecast


import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Wind(
    val direction: String?,
    val name: String?,
    val speedmax: Double?,
    val speedmin: Double?
)