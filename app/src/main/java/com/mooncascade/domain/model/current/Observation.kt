package com.mooncascade.domain.model.current


import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Observation(
    val airpressure: String?,
    val airtemperature: String?,
    val latitude: String?,
    val longitude: String?,
    val name: String?,
    val phenomenon: String?,
    val precipitations: String?,
    val relativehumidity: String?,
    val uvindex: String?,
    val visibility: String?,
    val waterlevel: String?,
    val waterlevelEh2000: String?,
    val watertemperature: String?,
    val winddirection: String?,
    val windspeed: String?,
    val windspeedmax: String?,
    val wmocode: String?
) : Parcelable