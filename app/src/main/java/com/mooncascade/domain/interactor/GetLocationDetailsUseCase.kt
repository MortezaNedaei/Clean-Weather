package com.mooncascade.domain.interactor

import com.mooncascade.domain.model.DataState
import com.mooncascade.di.qualifier.IoDispatcher
import com.mooncascade.domain.model.location.Location
import com.mooncascade.domain.respository.LocationDetailsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
) : UseCase<LocationWeatherParams?, Flow<DataState<Location?>>>(coroutineDispatcher) {

    override suspend fun execute(parameters: LocationWeatherParams?): Flow<DataState<Location?>> =
        flow {
            emit(DataState.Loading)
            repository.fetchLocationWeather(parameters?.locationId!!).collect { result ->
                if (result.isSuccess)
                    emit(DataState.Success(result.getOrNull()))
                else
                    emit(DataState.Error(result.exceptionOrNull()?.localizedMessage ?: ""))
            }

        }
}