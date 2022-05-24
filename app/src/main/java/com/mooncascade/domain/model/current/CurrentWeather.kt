package com.mooncascade.domain.model.current

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class CurrentWeather(
    val timestamp: String,
    val observations: List<Observation>,
) : Parcelable
