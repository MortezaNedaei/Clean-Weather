package com.mooncascade.data.cache.datastore

import android.util.Log
import com.mooncascade.common.extensions.TAG
import com.mooncascade.data.mapper.observation.toDomain
import com.mooncascade.data.mapper.observation.toEntity
import com.mooncascade.di.qualifier.IoDispatcher
import com.mooncascade.domain.model.current.Observation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ObservationLocalDataSourceImp @Inject constructor(
    private val dataStore: ObservationDataStore,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ObservationLocalDataSource {


    override suspend fun getObservations(): Flow<List<Observation>> {
        return dataStore.getObservations()
            .flowOn(dispatcher)
            .catch { Log.e(TAG, "${it.message}") }
            .map { it.toDomain() }
    }

    override suspend fun getItem(id: Long): Flow<Observation?> {
        return dataStore.getObservation(id)
            .flowOn(dispatcher)
            .catch { Log.e(TAG, "${it.message}") }
            .map { it?.toDomain() }
    }

    override suspend fun saveObservations(list: List<Observation>): Flow<List<Observation>> =
        withContext(dispatcher) {
            dataStore.saveObservations(list.toEntity())
                .flowOn(dispatcher)
                .catch { Log.e(TAG, "${it.message}") }
                .map { it.toDomain() }
        }


}