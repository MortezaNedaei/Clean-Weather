package com.mooncascade.domain.model.forecast


import androidx.annotation.Keep

@Keep
data class Wind(
    val direction: String?,
    val name: String?,
    val speedmax: Int?,
    val speedmin: Int?
)