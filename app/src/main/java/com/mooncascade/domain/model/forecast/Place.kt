package com.mooncascade.domain.model.forecast


import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Place(
    val id: String,
    val name: String?,
    val phenomenon: String?,
    val tempmax: Int?,
    val tempmin: Int?
) : Parcelable