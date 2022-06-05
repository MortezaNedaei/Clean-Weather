package com.mooncascade.domain.model.location


import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Relativehumidity(
    val units: String?,
    val value: String?
)