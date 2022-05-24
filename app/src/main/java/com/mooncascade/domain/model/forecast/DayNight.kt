package com.mooncascade.domain.model.forecast


import androidx.annotation.Keep

@Keep
data class DayNight(
    val peipsi: String?,
    val phenomenon: String?,
    val places: List<Place>?,
    val sea: String?,
    val tempmax: Int?,
    val tempmin: Int?,
    val text: String?,
    val winds: List<Wind>?
)