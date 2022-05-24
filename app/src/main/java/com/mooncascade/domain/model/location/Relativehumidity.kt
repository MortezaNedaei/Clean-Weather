package com.mooncascade.domain.model.location


import androidx.annotation.Keep

@Keep
data class Relativehumidity(
    val units: String?,
    val value: String?
)