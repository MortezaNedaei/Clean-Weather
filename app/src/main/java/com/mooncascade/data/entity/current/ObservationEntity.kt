package com.mooncascade.data.entity.current


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ObservationEntity(
    @SerializedName("airpressure")
    val airpressure: String?,
    @SerializedName("airtemperature")
    val airtemperature: String?,
    @SerializedName("latitude")
    val latitude: String?,
    @SerializedName("longitude")
    val longitude: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phenomenon")
    val phenomenon: String?,
    @SerializedName("precipitations")
    val precipitations: String?,
    @SerializedName("relativehumidity")
    val relativehumidity: String?,
    @SerializedName("uvindex")
    val uvindex: String?,
    @SerializedName("visibility")
    val visibility: String?,
    @SerializedName("waterlevel")
    val waterlevel: String?,
    @SerializedName("waterlevel_eh2000")
    val waterlevelEh2000: String?,
    @SerializedName("watertemperature")
    val watertemperature: String?,
    @SerializedName("winddirection")
    val winddirection: String?,
    @SerializedName("windspeed")
    val windspeed: String?,
    @SerializedName("windspeedmax")
    val windspeedmax: String?,
    @SerializedName("wmocode")
    val wmocode: String?
) : Parcelable