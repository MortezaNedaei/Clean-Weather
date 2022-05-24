package com.mooncascade.data.cache.datastore

import com.mooncascade.data.cache.dao.ForecastDao
import com.mooncascade.data.entity.forecast.ForecastEntity
import com.mooncascade.di.qualifier.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ForecastDataStore @Inject constructor(
    private val dao: ForecastDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {


    suspend fun getForecasts(): Flow<List<ForecastEntity>> = withContext(dispatcher) {
        dao.getAll()
    }


    suspend fun getForecast(id: Long): Flow<ForecastEntity?> = withContext(dispatcher) {
        dao.getItem(id)
    }

    suspend fun saveForecasts(list: List<ForecastEntity>): Flow<List<ForecastEntity>> =
        withContext(dispatcher) {
            dao.deleteAll()
            dao.insert(list)
            dao.getAll()
        }
}