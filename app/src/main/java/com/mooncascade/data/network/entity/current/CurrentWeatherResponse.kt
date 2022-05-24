package com.mooncascade.data.network.entity.current

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class CurrentWeatherResponse(
    @SerializedName("timestamp")
    val timestamp: String,
    @SerializedName("observations")
    val observations: List<ObservationResponse>,
) : Parcelable
