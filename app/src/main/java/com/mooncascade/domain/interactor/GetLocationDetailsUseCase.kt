package com.mooncascade.domain.interactor

import com.mooncascade.di.qualifier.IoDispatcher
import com.mooncascade.domain.model.location.Location
import com.mooncascade.domain.respository.LocationDetailsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Used for holding [GetLocationDetailsUseCase] query params like search or paging configs, etc.
 */
data class LocationWeatherParams(
    val locationId: Int
)

class GetLocationDetailsUseCase @Inject constructor(
    private val repository: LocationDetailsRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : UseCase<LocationWeatherParams?, Flow<Result<Location?>>>(coroutineDispatcher) {

    override suspend fun execute(parameters: LocationWeatherParams?): Flow<Result<Location?>> =
        repository.fetchLocationWeather(parameters?.locationId!!)
}