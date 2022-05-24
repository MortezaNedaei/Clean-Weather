package com.mooncascade.data.respository

import android.util.Log
import com.mooncascade.common.extensions.TAG
import com.mooncascade.data.cache.datastore.ForecastLocalDataSourceImp
import com.mooncascade.data.network.datasource.ForecastRemoteDataSourceImp
import com.mooncascade.data.respository.datasource.base.BaseDataSource
import com.mooncascade.di.qualifier.IoDispatcher
import com.mooncascade.domain.model.forecast.Forecast
import com.mooncascade.domain.respository.ForecastRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ForecastDataRepository @Inject constructor(
    private val remoteDataSource: ForecastRemoteDataSourceImp,
    private val localDataSource: ForecastLocalDataSourceImp,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : BaseDataSource(), ForecastRepository {


    override suspend fun fetchForecasts(strategy: CacheStrategy): Flow<Result<List<Forecast>?>> {
        return fetchForecastsByStrategy(strategy)
    }

    private suspend fun fetchForecastsByStrategy(cacheStrategy: CacheStrategy) =
        flow<Result<List<Forecast>>> {
            when (cacheStrategy) {
                CacheStrategy.ONLINE_FIRST -> {
                    fetchOnlineFirst()
                        .flowOn(coroutineDispatcher)
                        .catch { emit(Result.failure(Exception(it))) }
                        .collect { emit(Result.success(it)) }
                }
                CacheStrategy.OFFLINE_FIRST -> {
                    fetchOfflineFirst()
                        .flowOn(coroutineDispatcher)
                        .catch { emit(Result.failure(Exception(it))) }
                        .collect { emit(Result.success(it)) }
                }
            }
        }


    /**
     * OnlineFirst: fetch from remote and save, then use the new saved data. use former cached data
     * if network request is failed
     */
    private suspend fun fetchOnlineFirst() = flow {
        fetchRemoteForecasts()
            .flowOn(coroutineDispatcher)
            .catch { Log.e(TAG, "${it.message}") }
            .collect { result ->
                // Save remote data in cache and emit cached data if network request is successful
                if (result.isSuccess) {
                    saveForecasts(result.getOrNull() ?: emptyList())
                        .collect { newCachedData -> emit(newCachedData) }
                } else {
                    // Just emit the cached data from before, if network request is failed
                    fetchLocalForecasts()
                        .flowOn(coroutineDispatcher)
                        .catch { Log.e(TAG, "${it.message}") }
                        .collect { cachedData -> emit(cachedData) }
                }
            }
    }

    /**
     * OfflineFirst: fetch from db, if db records are empty, make a network request and get new data and save it.
     */
    private suspend fun fetchOfflineFirst() = flow {
        fetchLocalForecasts()
            .flowOn(coroutineDispatcher)
            .catch {
                Log.e(TAG, "${it.message}")
            }
            .collect { result ->
                if (result.isEmpty()) {
                    // fetch remote data and save in cache and emit cached data if there is no records in db
                    fetchFromRemoteAndSave()
                        .flowOn(coroutineDispatcher)
                        .catch {
                            Log.e(TAG, "${it.message}")
                        }
                        .collect { emit(it) }
                } else {
                    // You can check cache policies like maximum possible date to use offline here.
                    emit(result)
                }
            }
    }


    /**
     * Get fresh data and save, If cache data is invalid or null
     */
    private suspend fun fetchFromRemoteAndSave() = flow {
        fetchRemoteForecasts()
            .flowOn(coroutineDispatcher)
            .catch { Log.e(TAG, "${it.message}") }
            .collect { result ->
                // Save remote data in cache and emit cached data if network request is successful
                saveForecasts(result.getOrNull() ?: emptyList())
                    .collect { newCachedData -> emit(newCachedData) }
            }
    }

    private suspend fun fetchRemoteForecasts() = remoteDataSource.getForecasts()

    private suspend fun fetchLocalForecasts() = localDataSource.getForecasts()

    private suspend fun saveForecasts(
        list: List<Forecast>
    ) = localDataSource.saveForecasts(list)
}