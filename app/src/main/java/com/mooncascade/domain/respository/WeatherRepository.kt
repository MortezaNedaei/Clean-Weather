package com.mooncascade.domain.respository

import com.mooncascade.data.entity.current.CurrentWeatherEntity
import com.mooncascade.data.entity.forecast.NextDaysForecastEntity
import com.mooncascade.data.entity.location.LocationEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Path

interface WeatherRepository {

    suspend fun getNextDaysForecasts(): Flow<Result<NextDaysForecastEntity>>

    suspend fun getCurrentWeather(): Flow<Result<CurrentWeatherEntity>>

    suspend fun getLocationWeather(
        @Path("id") locationId: Int
    ): Flow<Result<LocationEntity>>
}