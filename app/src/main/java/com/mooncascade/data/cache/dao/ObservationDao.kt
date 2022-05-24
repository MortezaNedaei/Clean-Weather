package com.mooncascade.data.cache.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.mooncascade.data.cache.base.BaseDao
import com.mooncascade.data.entity.observation.ObservationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ObservationDao : BaseDao<ObservationEntity> {

    companion object {
        const val
                TABLE_NAME = "observation"
        private const val GET_ALL = "SELECT * FROM $TABLE_NAME"
        private const val GET_ITEM = "SELECT * FROM $TABLE_NAME WHERE id LIKE :id"
        private const val DELETE_ALL = "Delete FROM $TABLE_NAME"
    }

    @Query(GET_ALL)
    fun getAll(): Flow<List<ObservationEntity>>

    @Query(GET_ALL)
    fun getAllPaginated(): PagingSource<Int, ObservationEntity>

    @Query(GET_ITEM)
    fun getItem(id: Long): Flow<ObservationEntity?>

    @Query(DELETE_ALL)
    suspend fun deleteAll()
}
