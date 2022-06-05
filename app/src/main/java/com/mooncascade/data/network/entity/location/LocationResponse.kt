package com.mooncascade.data.network.entity.location


import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class LocationResponse(
    @SerialName("cloudbase")
    val cloudbase: String?,
    @SerialName("cloudcoverlow")
    val cloudcoverlow: String?,
    @SerialName("cloudcovertotal")
    val cloudcovertotal: String?,
    @SerialName("cloudtypehigh")
    val cloudtypehigh: String?,
    @SerialName("cloudtypelow")
    val cloudtypelow: String?,
    @SerialName("cloudtypemed")
    val cloudtypemed: String?,
    @SerialName("code")
    val code: String?,
    @SerialName("dewpoint")
    val dewpoint: DewpointResponse?,
    @SerialName("mode")
    val mode: String?,
    @SerialName("name_eng")
    val nameEng: String?,
    @SerialName("name_est")
    val nameEst: String?,
    @SerialName("precipitations")
    val precipitations: PrecipitationsResponse?,
    @SerialName("precipitationsperiod")
    val precipitationsperiod: PrecipitationsperiodResponse?,
    @SerialName("presentweather")
    val presentweather: String?,
    @SerialName("pressure")
    val pressure: PressureResponse?,
    @SerialName("pressurechange")
    val pressurechange: PressurechangeResponse?,
    @SerialName("pressuretendency")
    val pressuretendency: String?,
    @SerialName("relativehumidity")
    val relativehumidity: RelativehumidityResponse?,
    @SerialName("temperature")
    val temperature: TemperatureResponse?,
    @SerialName("visibility")
    val visibility: String?,
    @SerialName("wind")
    val wind: WindResponse?,
    @SerialName("winddirection")
    val winddirection: WinddirectionResponse?
)