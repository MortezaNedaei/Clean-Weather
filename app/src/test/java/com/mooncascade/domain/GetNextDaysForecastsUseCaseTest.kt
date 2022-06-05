package com.mooncascade.domain

import com.mooncascade.domain.interactor.GetNextDaysForecastsUseCase
import com.mooncascade.domain.model.forecast.Forecast
import com.mooncascade.domain.model.mock.nextDaysForecastsMockData
import com.mooncascade.domain.respository.ForecastRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

class GetNextDaysForecastsUseCaseTest {

    private val repository = mockk<ForecastRepository>()
    private val coroutineDispatcher = mockk<CoroutineDispatcher>()
    private val useCase = GetNextDaysForecastsUseCase(
        repository, coroutineDispatcher
    )


    @Test
    fun `should return next days forecasts from ForecastRepository if no error`() {

        val model = Json.decodeFromString<List<Forecast>>(nextDaysForecastsMockData)

        coEvery { repository.fetchForecasts() } returns flowOf(Result.success(model))

        suspend {
            val response = useCase.invoke()
            assertEquals(response, flowOf(Result.success(model)))
        }
    }


}