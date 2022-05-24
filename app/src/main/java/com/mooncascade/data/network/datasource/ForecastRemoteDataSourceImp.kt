package com.mooncascade.data.network.datasource

import android.util.Log
import com.mooncascade.data.mapper.forecast.toDomain
import com.mooncascade.data.network.service.WeatherApi
import com.mooncascade.data.respository.datasource.base.BaseDataSource
import com.mooncascade.data.respository.datasource.forecast.ForecastRemoteDataSource
import com.mooncascade.domain.model.forecast.Forecast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ForecastRemoteDataSourceImp @Inject constructor(
    private val weatherApi: WeatherApi
) : BaseDataSource(), ForecastRemoteDataSource {

    companion object {
        const val TAG = "ForecastRemoteDataSourceImp"
    }

    override suspend fun getForecasts(): Flow<Result<List<Forecast>>> = flow {
        val response = getResult { weatherApi.getNextDaysForecasts() }
        response
            .catch { Log.e(TAG, "${it.message}") }
            .collect { result ->
                if (result.isSuccess) {
                    emit(
                        Result.success(
                            result.getOrNull()?.forecasts?.toDomain() ?: emptyList()
                        )
                    )
                } else {
                    emit(Result.failure(Exception(result.exceptionOrNull())))
                }
            }
    }


}