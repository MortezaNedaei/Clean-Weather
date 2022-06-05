package com.mooncascade.data.network.entity.current


import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ObservationResponse(
    @SerialName("airpressure")
    val airpressure: String?,
    @SerialName("airtemperature")
    val airtemperature: String?,
    @SerialName("latitude")
    val latitude: String?,
    @SerialName("longitude")
    val longitude: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("phenomenon")
    val phenomenon: String?,
    @SerialName("precipitations")
    val precipitations: String?,
    @SerialName("relativehumidity")
    val relativehumidity: String?,
    @SerialName("uvindex")
    val uvindex: String?,
    @SerialName("visibility")
    val visibility: String?,
    @SerialName("waterlevel")
    val waterlevel: String?,
    @SerialName("waterlevel_eh2000")
    val waterlevelEh2000: String?,
    @SerialName("watertemperature")
    val watertemperature: String?,
    @SerialName("winddirection")
    val winddirection: String?,
    @SerialName("windspeed")
    val windspeed: String?,
    @SerialName("windspeedmax")
    val windspeedmax: String?,
    @SerialName("wmocode")
    val wmocode: String?
)