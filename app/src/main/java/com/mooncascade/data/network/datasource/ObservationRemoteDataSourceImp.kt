package com.mooncascade.data.network.datasource

import android.util.Log
import com.mooncascade.data.mapper.toDomain
import com.mooncascade.data.network.service.WeatherApi
import com.mooncascade.data.respository.datasource.base.BaseDataSource
import com.mooncascade.data.respository.datasource.observation.ObservationRemoteDataSource
import com.mooncascade.domain.model.current.Observation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ObservationRemoteDataSourceImp @Inject constructor(
    private val weatherApi: WeatherApi
) : BaseDataSource(), ObservationRemoteDataSource {

    companion object {
        const val TAG = "ObservationRemoteDataSourceImp"
    }

    override suspend fun getObservations(): Flow<Result<List<Observation>>> = flow {
        val response = getResult { weatherApi.getCurrentWeather() }
        response
            .catch { Log.e(TAG, "${it.message}") }
            .collect { result ->
                if (result.isSuccess) {
                    emit(
                        Result.success(
                            result.getOrNull()?.observations?.toDomain() ?: emptyList()
                        )
                    )
                } else {
                    emit(Result.failure(Exception(result.exceptionOrNull())))
                }
            }
    }


}