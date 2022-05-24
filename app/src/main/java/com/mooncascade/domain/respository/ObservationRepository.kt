package com.mooncascade.domain.respository

import com.mooncascade.data.respository.CacheStrategy
import com.mooncascade.domain.model.current.Observation
import kotlinx.coroutines.flow.Flow

interface ObservationRepository {

    suspend fun fetchObservations(
        strategy: CacheStrategy = CacheStrategy.ONLINE_FIRST
    ): Flow<Result<List<Observation>?>>
}