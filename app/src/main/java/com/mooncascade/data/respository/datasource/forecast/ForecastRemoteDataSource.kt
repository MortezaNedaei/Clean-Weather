package com.mooncascade.data.respository.datasource.forecast

import com.mooncascade.domain.model.forecast.Forecast
import kotlinx.coroutines.flow.Flow

interface ForecastRemoteDataSource {

    suspend fun getForecasts(): Flow<Result<List<Forecast>>>
}