package com.mooncascade.data.respository

import com.mooncascade.data.entity.current.CurrentWeatherEntity
import com.mooncascade.data.entity.forecast.NextDaysForecastEntity
import com.mooncascade.data.entity.location.LocationEntity
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

    override suspend fun getNextDaysForecasts(): Flow<Result<NextDaysForecastEntity>> {
        // TODO check network here or in the BaseDataSource
        val response = weatherApi.getNextDaysForecasts()
        return getResult { response }
    }

    override suspend fun getCurrentWeather(): Flow<Result<CurrentWeatherEntity>> {
        // TODO check network here or in the BaseDataSource
        val response = weatherApi.getCurrentWeather()
        return getResult { response }
    }

    override suspend fun getLocationWeather(locationId: Int): Flow<Result<LocationEntity>> {
        // TODO check network here or in the BaseDataSource
        val response = weatherApi.getLocationWeather(locationId)
        return getResult { response }
    }
}