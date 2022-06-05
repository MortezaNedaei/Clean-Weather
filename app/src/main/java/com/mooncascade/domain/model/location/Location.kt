package com.mooncascade.domain.model.location


import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Location(
    val cloudbase: String?,
    val cloudcoverlow: String?,
    val cloudcovertotal: String?,
    val cloudtypehigh: String?,
    val cloudtypelow: String?,
    val cloudtypemed: String?,
    val code: String?,
    val dewpoint: Dewpoint?,
    val mode: String?,
    val nameEng: String?,
    val nameEst: String?,
    val precipitations: Precipitations?,
    val precipitationsperiod: Precipitationsperiod?,
    val presentweather: String?,
    val pressure: Pressure?,
    val pressurechange: Pressurechange?,
    val pressuretendency: String?,
    val relativehumidity: Relativehumidity?,
    val temperature: Temperature?,
    val visibility: String?,
    val wind: Wind?,
    val winddirection: Winddirection?
)