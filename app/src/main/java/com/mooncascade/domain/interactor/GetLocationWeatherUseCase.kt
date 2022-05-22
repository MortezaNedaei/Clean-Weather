package com.mooncascade.domain.interactor

import com.mooncascade.data.entity.location.LocationEntity
import com.mooncascade.di.qualifier.IoDispatcher
import com.mooncascade.domain.respository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Used for holding [GetLocationWeatherUseCase] query params like search or paging configs, etc.
 */
data class LocationWeatherParams(
    val locationId: Int
)

class GetLocationWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : UseCase<LocationWeatherParams?, Flow<Result<LocationEntity>>>(coroutineDispatcher) {

    override suspend fun execute(parameters: LocationWeatherParams?): Flow<Result<LocationEntity>> =
        repository.getLocationWeather(parameters?.locationId!!)
}