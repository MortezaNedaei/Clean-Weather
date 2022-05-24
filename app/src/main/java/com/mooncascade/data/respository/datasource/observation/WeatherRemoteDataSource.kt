package com.mooncascade.data.respository.datasource.observation

import com.mooncascade.data.network.entity.forecast.NextDaysForecastResponse
import com.mooncascade.data.network.entity.location.LocationResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRemoteDataSource {

    suspend fun getNextDaysForecasts(): Flow<Result<NextDaysForecastResponse>>

    suspend fun getLocationWeather(locationId: Int): Flow<Result<LocationResponse>>
}