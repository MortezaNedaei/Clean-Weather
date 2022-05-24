package com.mooncascade.data.cache.datastore

import com.mooncascade.domain.model.forecast.Forecast
import kotlinx.coroutines.flow.Flow

interface ForecastLocalDataSource {

    suspend fun getForecasts(): Flow<List<Forecast>>

    suspend fun getItem(id: Long): Flow<Forecast?>

    suspend fun saveForecasts(list: List<Forecast>): Flow<List<Forecast>>
}