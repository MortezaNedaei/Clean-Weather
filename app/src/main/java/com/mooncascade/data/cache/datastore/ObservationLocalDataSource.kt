package com.mooncascade.data.cache.datastore

import com.mooncascade.domain.model.current.Observation
import kotlinx.coroutines.flow.Flow

interface ObservationLocalDataSource {

    suspend fun getObservations(): Flow<List<Observation>>

    suspend fun getItem(id: Long): Flow<Observation?>

    suspend fun saveObservations(list: List<Observation>): Flow<List<Observation>>
}