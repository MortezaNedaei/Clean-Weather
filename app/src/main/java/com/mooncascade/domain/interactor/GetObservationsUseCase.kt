package com.mooncascade.domain.interactor

import com.mooncascade.di.qualifier.IoDispatcher
import com.mooncascade.domain.model.current.Observation
import com.mooncascade.domain.respository.ObservationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Used for holding future [GetObservationsUseCase] query params like search or paging configs, etc.
 * You can change it to data class if there is any param now.
 */
class ObservationsUseCaseParams

class GetObservationsUseCase @Inject constructor(
    private val repository: ObservationRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : UseCase<ObservationsUseCaseParams?, Flow<Result<List<Observation>?>>>(coroutineDispatcher) {

    override suspend fun execute(parameters: ObservationsUseCaseParams?): Flow<Result<List<Observation>?>> =
        repository.fetchObservations()
}