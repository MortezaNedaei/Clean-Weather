package com.mooncascade.data.entity.current

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class CurrentWeatherEntity(
    @SerializedName("timestamp")
    val timestamp: String,
    @SerializedName("observations")
    val observations: List<ObservationEntity>,
) : Parcelable
