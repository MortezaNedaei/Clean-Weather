package com.mooncascade.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mooncascade.data.cache.place.PlaceDao
import com.mooncascade.data.entity.forecast.PlaceEntity


@Database(
    entities = [
        PlaceEntity::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [AppTypeConverters::class])
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "clean_weather.db"
    }

    abstract fun placeDao(): PlaceDao
}