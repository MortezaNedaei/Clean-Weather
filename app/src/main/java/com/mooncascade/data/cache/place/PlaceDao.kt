package com.mooncascade.data.cache.place

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.mooncascade.data.cache.base.BaseDao
import com.mooncascade.data.entity.forecast.PlaceEntity
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao : BaseDao<PlaceEntity> {

    private object Queries {
        const val GET_ALL = "SELECT * FROM place"
        const val GET_ITEM = "SELECT * FROM place WHERE id LIKE :id"
        const val DELETE_ALL = "Delete FROM place"
    }

    @Query(Queries.GET_ALL)
    fun getAllPaginated(): PagingSource<Int, PlaceEntity>

    @Query(Queries.GET_ITEM)
    fun getItem(id: String): Flow<PlaceEntity?>

    @Query(Queries.DELETE_ALL)
    suspend fun deleteAll()
}
