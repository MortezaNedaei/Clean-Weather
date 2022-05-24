package com.mooncascade.data.network.entity.forecast


import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class PlaceResponse(
    @SerializedName("name")
    val name: String?,
    @SerializedName("phenomenon")
    val phenomenon: String?,
    @SerializedName("tempmax")
    val tempmax: Int?,
    @SerializedName("tempmin")
    val tempmin: Int?
) : Parcelable