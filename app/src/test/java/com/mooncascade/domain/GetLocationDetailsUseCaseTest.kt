package com.mooncascade.domain

import com.mooncascade.domain.interactor.GetLocationDetailsUseCase
import com.mooncascade.domain.interactor.LocationWeatherParams
import com.mooncascade.domain.model.location.Location
import com.mooncascade.domain.model.mock.locationWeatherMockData
import com.mooncascade.domain.respository.LocationDetailsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

class GetLocationDetailsUseCaseTest {

    private val repository = mockk<LocationDetailsRepository>()
    private val coroutineDispatcher = mockk<CoroutineDispatcher>()
    private val useCase = GetLocationDetailsUseCase(
        repository, coroutineDispatcher
    )


    @Test
    fun `should return location weather from LocationDetailsRepository if no error`() {

        val model = Json.decodeFromString<Location?>(locationWeatherMockData)

        coEvery { repository.fetchLocationWeather(0) } returns flowOf(Result.success(model))

        suspend {
            val response = useCase.invoke(LocationWeatherParams(0))
            assertEquals(response, flowOf(Result.success(model)))
        }
    }


}