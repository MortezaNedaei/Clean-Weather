package com.mooncascade.domain

import com.google.gson.Gson
import com.mooncascade.data.entity.location.LocationEntity
import com.mooncascade.data.respository.WeatherDataRepository
import com.mooncascade.domain.interactor.GetLocationWeatherUseCase
import com.mooncascade.domain.interactor.LocationWeatherParams
import com.mooncascade.domain.model.mock.locationWeatherMockData
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Test

class GetLocationWeatherUseCaseTest {

    private val repository = mockk<WeatherDataRepository>()
    private val coroutineDispatcher = mockk<CoroutineDispatcher>()
    private val locationWeatherUseCase = GetLocationWeatherUseCase(
        repository, coroutineDispatcher
    )


    @Test
    fun `should return location weather from WeatherRepository if no error`() {

        val weatherEntity =
            Gson().fromJson(
                locationWeatherMockData,
                LocationEntity::class.java
            )
        coEvery { repository.getLocationWeather(0) } returns flowOf(Result.success(weatherEntity))

        suspend {
            val response = locationWeatherUseCase.invoke(LocationWeatherParams(0))
            assertEquals(response, flowOf(Result.success(weatherEntity)))
        }
    }


}