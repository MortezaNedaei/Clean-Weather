package com.mooncascade.domain.model.location


import androidx.annotation.Keep

@Keep
data class Temperature(
    val units: String?,
    val value: String?
)