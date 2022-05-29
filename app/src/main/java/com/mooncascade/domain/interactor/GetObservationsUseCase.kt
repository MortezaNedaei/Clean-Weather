package com.mooncascade.domain.interactor

import com.mooncascade.domain.model.DataState
import com.mooncascade.di.qualifier.IoDispatcher
import com.mooncascade.domain.model.current.Observation
import com.mooncascade.domain.respository.ObservationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Used for holding future [GetObservationsUseCase] query params like search or paging configs, etc.
 * You can change it to data class if there is any param now.
 */
class ObservationsUseCaseParams

class GetObservationsUseCase @Inject constructor(
    private val repository: ObservationRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : UseCase<ObservationsUseCaseParams?, Flow<DataState<List<Observation>?>>>(coroutineDispatcher) {

    override suspend fun execute(parameters: ObservationsUseCaseParams?): Flow<DataState<List<Observation>?>> =
        flow {
            emit(DataState.Loading)
            repository.fetchObservations().collect { result ->
                if (result.isSuccess)
                    emit(DataState.Success(result.getOrNull()))
                else
                    emit(DataState.Error(result.exceptionOrNull()?.localizedMessage ?: ""))
            }

        }

}