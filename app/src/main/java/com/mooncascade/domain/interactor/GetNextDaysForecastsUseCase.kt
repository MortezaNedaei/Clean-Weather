package com.mooncascade.domain.interactor

import com.mooncascade.domain.model.DataState
import com.mooncascade.di.qualifier.IoDispatcher
import com.mooncascade.domain.model.forecast.Forecast
import com.mooncascade.domain.respository.ForecastRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Used for holding future [GetNextDaysForecastsUseCase] params like locationId, search or paging configs, etc.
 * You can change it to data class if there is any param now.
 */
class ForecastParams

class GetNextDaysForecastsUseCase @Inject constructor(
    private val repository: ForecastRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : UseCase<ForecastParams?, Flow<DataState<List<Forecast>?>>>(coroutineDispatcher) {

    override suspend fun execute(parameters: ForecastParams?): Flow<DataState<List<Forecast>?>> =
        flow {
            emit(DataState.Loading)
            repository.fetchForecasts().collect { result ->
                if (result.isSuccess)
                    emit(DataState.Success(result.getOrNull()))
                else
                    emit(DataState.Error(result.exceptionOrNull()?.localizedMessage ?: ""))
            }

        }
}