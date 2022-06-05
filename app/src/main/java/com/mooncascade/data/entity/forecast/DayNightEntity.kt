package com.mooncascade.data.entity.forecast


import androidx.annotation.Keep

@Keep
data class DayNightEntity(
    val peipsi: String?,
    val phenomenon: String?,
//    val places: List<PlaceEntity>?,
    val sea: String?,
    val tempmax: Double?,
    val tempmin: Double?,
    val text: String?,
//    val winds: List<WindEntity>?
)