package com.mooncascade.domain.respository

import com.mooncascade.data.entity.current.CurrentWeatherEntity
import com.mooncascade.data.entity.forecast.NextDaysForecastEntity
import com.mooncascade.data.entity.location.LocationEntity
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getNextDaysForecasts(): Flow<Result<NextDaysForecastEntity>>

    suspend fun getCurrentWeather(): Flow<Result<CurrentWeatherEntity>>

    suspend fun getLocationWeather(locationId: Int): Flow<Result<LocationEntity>>
}