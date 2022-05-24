package com.mooncascade.data.entity.observation


import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mooncascade.data.cache.dao.ObservationDao

@Keep
@Entity(
    tableName = ObservationDao.TABLE_NAME,
    indices = [Index(value = ["id"], unique = true)]
)
data class ObservationEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val airpressure: String?,
    val airtemperature: String?,
    val latitude: String?,
    val longitude: String?,
    val name: String?,
    val phenomenon: String?,
    val precipitations: String?,
    val relativehumidity: String?,
    val uvindex: String?,
    val visibility: String?,
    val waterlevel: String?,
    val waterlevelEh2000: String?,
    val watertemperature: String?,
    val winddirection: String?,
    val windspeed: String?,
    val windspeedmax: String?,
    val wmocode: String?,
)