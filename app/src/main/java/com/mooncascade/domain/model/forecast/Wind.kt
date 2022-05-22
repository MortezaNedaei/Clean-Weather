package com.mooncascade.domain.model.forecast


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Wind(
    @SerializedName("direction")
    val direction: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("speedmax")
    val speedmax: Int?,
    @SerializedName("speedmin")
    val speedmin: Int?
)