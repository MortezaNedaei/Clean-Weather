package com.mooncascade.data.network.entity.location


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PressurechangeResponse(
    @SerializedName("units")
    val units: String?,
    @SerializedName("value")
    val value: String?
)