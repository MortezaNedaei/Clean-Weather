package com.mooncascade.data.entity.forecast


import androidx.annotation.Keep

@Keep
data class PlaceEntity(
    val name: String?,
    val phenomenon: String?,
    val tempmax: Int?,
    val tempmin: Int?
)