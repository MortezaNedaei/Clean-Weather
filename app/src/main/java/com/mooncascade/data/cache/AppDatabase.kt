package com.mooncascade.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mooncascade.data.cache.dao.ForecastDao
import com.mooncascade.data.cache.dao.ObservationDao
import com.mooncascade.data.entity.observation.ObservationEntity
import com.mooncascade.data.entity.forecast.ForecastEntity


@Database(
    entities = [
        ObservationEntity::class,
        ForecastEntity::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [AppTypeConverters::class])
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "clean_weather.db"
    }

    abstract fun observationDao(): ObservationDao

    abstract fun forecastDao(): ForecastDao
}