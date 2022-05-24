package com.mooncascade.data.cache.datastore

import android.util.Log
import com.mooncascade.common.extensions.TAG
import com.mooncascade.data.mapper.forecast.toDomain
import com.mooncascade.data.mapper.forecast.toEntity
import com.mooncascade.di.qualifier.IoDispatcher
import com.mooncascade.domain.model.forecast.Forecast
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ForecastLocalDataSourceImp @Inject constructor(
    private val dataStore: ForecastDataStore,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ForecastLocalDataSource {


    override suspend fun getForecasts(): Flow<List<Forecast>> {
        return dataStore.getForecasts()
            .flowOn(dispatcher)
            .catch { Log.e(TAG, "${it.message}") }
            .map { it.toDomain() }
    }

    override suspend fun getItem(id: Long): Flow<Forecast?> {
        return dataStore.getForecast(id)
            .flowOn(dispatcher)
            .catch { Log.e(TAG, "${it.message}") }
            .map { it?.toDomain() }
    }

    override suspend fun saveForecasts(list: List<Forecast>): Flow<List<Forecast>> =
        withContext(dispatcher) {
            dataStore.saveForecasts(list.toEntity())
                .flowOn(dispatcher)
                .catch { Log.e(TAG, "${it.message}") }
                .map { it.toDomain() }
        }


}