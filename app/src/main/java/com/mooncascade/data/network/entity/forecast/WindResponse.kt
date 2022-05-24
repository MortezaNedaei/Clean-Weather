package com.mooncascade.data.network.entity.forecast


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class WindResponse(
    @SerializedName("direction")
    val direction: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("speedmax")
    val speedmax: Int?,
    @SerializedName("speedmin")
    val speedmin: Int?
)