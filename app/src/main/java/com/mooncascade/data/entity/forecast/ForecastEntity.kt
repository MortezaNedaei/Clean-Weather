package com.mooncascade.data.entity.forecast


import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mooncascade.data.cache.dao.ForecastDao

@Keep
@Entity(
    tableName = ForecastDao.TABLE_NAME,
    indices = [Index(value = ["id"], unique = true)]
)
data class ForecastEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val date: String?,

    @Embedded(prefix = "day_")
    val day: DayNightEntity?,

    @Embedded(prefix = "night_")
    val night: DayNightEntity?
)