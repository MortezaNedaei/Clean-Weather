package com.mooncascade.data.network.entity.location


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LocationResponse(
    @SerializedName("cloudbase")
    val cloudbase: String?,
    @SerializedName("cloudcoverlow")
    val cloudcoverlow: String?,
    @SerializedName("cloudcovertotal")
    val cloudcovertotal: String?,
    @SerializedName("cloudtypehigh")
    val cloudtypehigh: String?,
    @SerializedName("cloudtypelow")
    val cloudtypelow: String?,
    @SerializedName("cloudtypemed")
    val cloudtypemed: String?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("dewpoint")
    val dewpoint: DewpointResponse?,
    @SerializedName("mode")
    val mode: String?,
    @SerializedName("name_eng")
    val nameEng: String?,
    @SerializedName("name_est")
    val nameEst: String?,
    @SerializedName("precipitations")
    val precipitations: PrecipitationsResponse?,
    @SerializedName("precipitationsperiod")
    val precipitationsperiod: PrecipitationsperiodResponse?,
    @SerializedName("presentweather")
    val presentweather: String?,
    @SerializedName("pressure")
    val pressure: PressureResponse?,
    @SerializedName("pressurechange")
    val pressurechange: PressurechangeResponse?,
    @SerializedName("pressuretendency")
    val pressuretendency: String?,
    @SerializedName("relativehumidity")
    val relativehumidity: RelativehumidityResponse?,
    @SerializedName("temperature")
    val temperature: TemperatureResponse?,
    @SerializedName("visibility")
    val visibility: String?,
    @SerializedName("wind")
    val wind: WindResponse?,
    @SerializedName("winddirection")
    val winddirection: WinddirectionResponse?
)