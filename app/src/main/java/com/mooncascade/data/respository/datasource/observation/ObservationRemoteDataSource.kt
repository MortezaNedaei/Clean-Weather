package com.mooncascade.data.respository.datasource.observation

import com.mooncascade.domain.model.current.Observation
import kotlinx.coroutines.flow.Flow

interface ObservationRemoteDataSource {

    suspend fun getObservations(): Flow<Result<List<Observation>>>
}