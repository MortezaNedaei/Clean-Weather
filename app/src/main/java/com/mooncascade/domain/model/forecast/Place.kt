package com.mooncascade.domain.model.forecast


import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Keep
@Parcelize
@Serializable
data class Place(
    val id: Long,
    val name: String?,
    val phenomenon: String?,
    val tempmax: Double?,
    val tempmin: Double?
) : Parcelable