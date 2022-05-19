package com.mooncascade.data.entity.location


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Winddirection(
    @SerializedName("units")
    val units: String?,
    @SerializedName("value")
    val value: String?
)