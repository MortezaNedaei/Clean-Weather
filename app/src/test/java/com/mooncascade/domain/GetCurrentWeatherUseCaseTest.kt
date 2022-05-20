package com.mooncascade.domain

import com.google.gson.Gson
import com.mooncascade.data.entity.current.CurrentWeatherEntity
import com.mooncascade.data.respository.WeatherDataRepository
import com.mooncascade.domain.interactor.GetCurrentWeatherUseCase
import com.mooncascade.domain.model.mock.currentWeatherMockData
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Test

class GetCurrentWeatherUseCaseTest {

    private val repository = mockk<WeatherDataRepository>()
    private val coroutineDispatcher = mockk<CoroutineDispatcher>()
    private val currentWeatherUseCase = GetCurrentWeatherUseCase(
        repository, coroutineDispatcher
    )


    @Test
    fun `should return current weather from WeatherRepository if no error`() {

        val currentWeather =
            Gson().fromJson(
                currentWeatherMockData,
                CurrentWeatherEntity::class.java
            )
        coEvery { repository.getCurrentWeather() } returns flowOf(Result.success(currentWeather))

        suspend {
            val response = currentWeatherUseCase.invoke()
            assertEquals(response, flowOf(Result.success(currentWeather)))
        }
    }


}