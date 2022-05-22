package com.mooncascade.data.entity.forecast


import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
@Entity(
    tableName = "Place",
    indices = [Index(value = ["id"], unique = true)]
)
data class PlaceEntity(
    @PrimaryKey val id: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phenomenon")
    val phenomenon: String?,
    @SerializedName("tempmax")
    val tempmax: Int?,
    @SerializedName("tempmin")
    val tempmin: Int?
) : Parcelable