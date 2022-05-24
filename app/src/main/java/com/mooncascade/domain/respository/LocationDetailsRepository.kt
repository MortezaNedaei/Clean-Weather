package com.mooncascade.domain.respository

import com.mooncascade.domain.model.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationDetailsRepository {

    suspend fun fetchLocationWeather(locationId: Int): Flow<Result<Location?>>
}