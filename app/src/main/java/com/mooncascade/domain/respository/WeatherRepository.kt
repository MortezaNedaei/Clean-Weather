package com.mooncascade.domain.respository

import com.mooncascade.data.entity.ForecastDataEntity
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getForecasts(): Flow<Result<ForecastDataEntity>>
}