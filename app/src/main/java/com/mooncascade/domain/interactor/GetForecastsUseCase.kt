package com.mooncascade.domain.interactor

import com.mooncascade.data.di.qualifier.IoDispatcher
import com.mooncascade.data.entity.ForecastDataEntity
import com.mooncascade.data.respository.WeatherDataRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Used for holding future [GetForecastsUseCase] params like locationId, search or paging configs, etc.
 * You can change it to data class if there is any param now.
 */
class ForecastParams

class GetForecastsUseCase @Inject constructor(
    private val repository: WeatherDataRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : UseCase<ForecastParams?, Flow<Result<ForecastDataEntity>>>(coroutineDispatcher) {

    override suspend fun execute(parameters: ForecastParams?): Flow<Result<ForecastDataEntity>> =
        repository.getForecasts()
}