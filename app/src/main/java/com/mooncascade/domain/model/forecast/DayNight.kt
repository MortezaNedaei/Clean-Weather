package com.mooncascade.domain.model.forecast


import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class DayNight(
    val peipsi: String?,
    val phenomenon: String?,
    val places: List<Place>?,
    val sea: String?,
    val tempmax: Double?,
    val tempmin: Double?,
    val text: String?,
    val winds: List<Wind>?
)