package com.mooncascade.domain.respository

import com.mooncascade.data.respository.CacheStrategy
import com.mooncascade.domain.model.forecast.Forecast
import kotlinx.coroutines.flow.Flow

interface ForecastRepository {

    suspend fun fetchForecasts(
        strategy: CacheStrategy = CacheStrategy.ONLINE_FIRST
    ): Flow<Result<List<Forecast>?>>
}