package com.mooncascade.domain

import com.google.gson.Gson
import com.mooncascade.data.entity.forecast.NextDaysForecastEntity
import com.mooncascade.data.respository.WeatherDataRepository
import com.mooncascade.domain.interactor.GetNextDaysForecastsUseCase
import com.mooncascade.domain.model.mock.nextDaysForecastsMockData
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Test

class GetNextDaysForecastsUseCaseTest {

    private val repository = mockk<WeatherDataRepository>()
    private val coroutineDispatcher = mockk<CoroutineDispatcher>()
    private val nextDaysForecastsUseCase = GetNextDaysForecastsUseCase(
        repository, coroutineDispatcher
    )


    @Test
    fun `should return next days forecasts from WeatherRepository if no error`() {

        val weatherEntity =
            Gson().fromJson(
                nextDaysForecastsMockData,
                NextDaysForecastEntity::class.java
            )
        coEvery { repository.getNextDaysForecasts() } returns flowOf(Result.success(weatherEntity))

        suspend {
            val response = nextDaysForecastsUseCase.invoke()
            assertEquals(response, flowOf(Result.success(weatherEntity)))
        }
    }


}