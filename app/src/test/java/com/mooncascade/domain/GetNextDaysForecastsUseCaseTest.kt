package com.mooncascade.domain

import com.google.gson.Gson
import com.mooncascade.domain.interactor.GetNextDaysForecastsUseCase
import com.mooncascade.domain.model.forecast.Forecast
import com.mooncascade.domain.model.mock.nextDaysForecastsMockData
import com.mooncascade.domain.respository.ForecastRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOf
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

        val model: List<Forecast> =
            Gson().fromJson(nextDaysForecastsMockData, Array<Forecast>::class.java)
                .toList()

        coEvery { repository.fetchForecasts() } returns flowOf(Result.success(model))

        suspend {
            val response = useCase.invoke()
            assertEquals(response, flowOf(Result.success(model)))
        }
    }


}