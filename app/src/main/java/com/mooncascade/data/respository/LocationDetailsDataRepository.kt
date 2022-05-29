package com.mooncascade.data.respository

import android.util.Log
import com.mooncascade.common.extensions.TAG
import com.mooncascade.data.mapper.location.toDomain
import com.mooncascade.data.network.service.WeatherApi
import com.mooncascade.data.respository.datasource.base.BaseDataSource
import com.mooncascade.di.qualifier.IoDispatcher
import com.mooncascade.domain.model.location.Location
import com.mooncascade.domain.respository.LocationDetailsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocationDetailsDataRepository @Inject constructor(
    private val weatherApi: WeatherApi,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : BaseDataSource(), LocationDetailsRepository {


    override suspend fun fetchLocationWeather(locationId: Int): Flow<Result<Location?>> = flow {
        val response = getResult { weatherApi.getLocationWeather(locationId) }
        response
            .flowOn(coroutineDispatcher)
            .catch { Log.e(TAG, "${it.message}") }
            .collect {
                emit(Result.success(it.getOrNull()?.toDomain()))
            }
    }

}