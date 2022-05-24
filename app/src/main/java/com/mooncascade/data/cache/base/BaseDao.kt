package com.mooncascade.data.cache.base

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<in T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: T): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(data: T)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(data: List<T>)

    @Delete
    suspend fun delete(data: T)
}