package com.mooncascade.data.cache.datastore

import androidx.paging.PagingSource
import com.mooncascade.data.cache.dao.ObservationDao
import com.mooncascade.data.entity.observation.ObservationEntity
import com.mooncascade.di.qualifier.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ObservationDataStore @Inject constructor(
    private val dao: ObservationDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    companion object {
        const val TAG = "ObservationDataStore"
    }

    suspend fun getObservations(): Flow<List<ObservationEntity>> = withContext(dispatcher) {
        dao.getAll()
    }

    suspend fun getObservationsPaginated(): PagingSource<Int, ObservationEntity> =
        withContext(dispatcher) {
            dao.getAllPaginated()
        }

    suspend fun getObservation(id: Long): Flow<ObservationEntity?> = withContext(dispatcher) {
        dao.getItem(id)
    }

    suspend fun saveObservations(list: List<ObservationEntity>): Flow<List<ObservationEntity>> =
        withContext(dispatcher) {
            dao.deleteAll()
            dao.insert(list)
            dao.getAll()
        }
}