package com.mooncascade.data.entity.forecast


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PlaceEntity(
    @SerializedName("name")
    val name: String?,
    @SerializedName("phenomenon")
    val phenomenon: String?,
    @SerializedName("tempmax")
    val tempmax: Int?,
    @SerializedName("tempmin")
    val tempmin: Int?
)