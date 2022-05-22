package com.mooncascade.domain.interactor

import com.mooncascade.data.entity.current.CurrentWeatherEntity
import com.mooncascade.di.qualifier.IoDispatcher
import com.mooncascade.domain.respository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Used for holding future [GetCurrentWeatherUseCase] query params like search or paging configs, etc.
 * You can change it to data class if there is any param now.
 */
class CurrentWeatherParams

class GetCurrentWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : UseCase<CurrentWeatherParams?, Flow<Result<CurrentWeatherEntity>>>(coroutineDispatcher) {

    override suspend fun execute(parameters: CurrentWeatherParams?): Flow<Result<CurrentWeatherEntity>> =
        repository.getCurrentWeather()
}