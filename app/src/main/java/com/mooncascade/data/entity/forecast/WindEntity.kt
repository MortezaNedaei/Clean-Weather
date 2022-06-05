package com.mooncascade.data.entity.forecast


import androidx.annotation.Keep

@Keep
data class WindEntity(
    val direction: String?,
    val name: String?,
    val speedmax: Double?,
    val speedmin: Double?
)