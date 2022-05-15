package com.mooncascade.data.respository

import com.mooncascade.data.entity.ForecastDataEntity
import com.mooncascade.data.network.WeatherApi
import com.mooncascade.domain.respository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDataRepository @Inject constructor(
    private val weatherApi: WeatherApi
) : BaseDataSource(), WeatherRepository {

    companion object {
        private const val TAG = "WeatherDataRepository"
    }

    override suspend fun getForecasts(): Flow<Result<ForecastDataEntity>> {
        // TODO check network here or in the BaseDataSource
        val response = weatherApi.getForecasts()
        return getResult { response }
    }
}