package com.mooncascade.data.entity.location


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LocationEntity(
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
    val dewpoint: DewpointEntity?,
    @SerializedName("mode")
    val mode: String?,
    @SerializedName("name_eng")
    val nameEng: String?,
    @SerializedName("name_est")
    val nameEst: String?,
    @SerializedName("precipitations")
    val precipitations: PrecipitationsEntity?,
    @SerializedName("precipitationsperiod")
    val precipitationsperiod: PrecipitationsperiodEntity?,
    @SerializedName("presentweather")
    val presentweather: String?,
    @SerializedName("pressure")
    val pressure: PressureEntity?,
    @SerializedName("pressurechange")
    val pressurechange: Pressurechange?,
    @SerializedName("pressuretendency")
    val pressuretendency: String?,
    @SerializedName("relativehumidity")
    val relativehumidity: RelativehumidityEntity?,
    @SerializedName("temperature")
    val temperature: TemperatureEntity?,
    @SerializedName("visibility")
    val visibility: String?,
    @SerializedName("wind")
    val wind: WindEntity?,
    @SerializedName("winddirection")
    val winddirection: Winddirection?
)