package com.mooncascade.domain

import com.google.gson.Gson
import com.mooncascade.domain.interactor.GetObservationsUseCase
import com.mooncascade.domain.model.current.Observation
import com.mooncascade.domain.model.mock.observationMockData
import com.mooncascade.domain.respository.ObservationRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Test


class GetObservationsUseCaseTest {

    private val repository = mockk<ObservationRepository>()
    private val coroutineDispatcher = mockk<CoroutineDispatcher>()
    private val useCase = GetObservationsUseCase(
        repository, coroutineDispatcher
    )


    @Test
    fun `should return observations from ObservationRepository if no error`() {

        val model: List<Observation> =
            Gson().fromJson(observationMockData, Array<Observation>::class.java)
                .toList()


        coEvery { repository.fetchObservations() } returns flowOf(Result.success(model))

        suspend {
            val response = useCase.invoke()
            assertEquals(response, flowOf(Result.success(model)))
        }
    }


}