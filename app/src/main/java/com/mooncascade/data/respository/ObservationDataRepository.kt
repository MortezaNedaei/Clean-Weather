package com.mooncascade.data.respository

import android.content.Context
import android.util.Log
import com.mooncascade.data.cache.datastore.ObservationLocalDataSourceImp
import com.mooncascade.data.network.datasource.ObservationRemoteDataSourceImp
import com.mooncascade.data.respository.datasource.base.BaseDataSource
import com.mooncascade.di.qualifier.IoDispatcher
import com.mooncascade.domain.model.current.Observation
import com.mooncascade.domain.respository.ObservationRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ObservationDataRepository @Inject constructor(
    private val remoteDataSource: ObservationRemoteDataSourceImp,
    private val localDataSource: ObservationLocalDataSourceImp,
    @ApplicationContext private val context: Context,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : BaseDataSource(), ObservationRepository {

    companion object {
        private const val TAG = "ObservationDataRepository"
    }

    override suspend fun fetchObservations(strategy: CacheStrategy): Flow<Result<List<Observation>>> {
        return fetchObservationsByStrategy(strategy)
    }


    private suspend fun fetchObservationsByStrategy(cacheStrategy: CacheStrategy) =
        flow<Result<List<Observation>>> {
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
     * [CacheStrategy.ONLINE_FIRST]: fetch from remote and save, then use the new saved data.
     * use former cached data network request is failed
     */
    private suspend fun fetchOnlineFirst() = flow {
        fetchRemoteObservations()
            .flowOn(coroutineDispatcher)
            .catch { Log.e(TAG, "${it.message}") }
            .collect { result ->
                // Save remote data in cache and emit cached data if network request is successful
                if (result.isSuccess) {
                    saveObservations(result.getOrNull() ?: emptyList())
                        .collect { newCachedData -> emit(newCachedData) }
                } else {
                    // Just emit the cached data from before, if network request is failed
                    fetchLocalObservations()
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
        fetchLocalObservations()
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
        fetchRemoteObservations()
            .flowOn(coroutineDispatcher)
            .catch { Log.e(TAG, "${it.message}") }
            .collect { result ->
                // Save remote data in cache and emit cached data if network request is successful
                saveObservations(result.getOrNull() ?: emptyList())
                    .collect { newCachedData -> emit(newCachedData) }
            }
    }

    private suspend fun fetchRemoteObservations() = remoteDataSource.getObservations()

    private suspend fun fetchLocalObservations() = localDataSource.getObservations()

    private suspend fun saveObservations(
        list: List<Observation>
    ) = localDataSource.saveObservations(list)

}