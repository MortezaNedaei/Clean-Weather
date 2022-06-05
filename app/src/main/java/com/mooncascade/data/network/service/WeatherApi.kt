package com.mooncascade.data.network.service

import com.mooncascade.data.entity.forecast.ForecastEntity
import com.mooncascade.data.network.entity.current.CurrentWeatherResponse
import com.mooncascade.data.network.entity.forecast.NextDaysForecastResponse
import com.mooncascade.data.network.entity.location.LocationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherApi {

    /**
     * used to hold the API endpoint constants
     */
    object Endpoints {
        const val GET_FORECASTS = "estonia/forecast/"
        const val GET_CURRENT_WEATHER = "estonia/current/"
        const val GET_LOCATION_WEATHER = "world/locations/{id}"
        const val GET_RANDOM_IMAGE = "1280/720/nature"
    }

    /**
     * retrieves forecasts for the next 4 days
     * @return forecasts: [ForecastEntity]
     */
    @GET(Endpoints.GET_FORECASTS)
    suspend fun getNextDaysForecasts(): Response<NextDaysForecastResponse>

    /**
     * retrieves current weather status
     * @return current weather: [CurrentWeatherResponse]
     */
    @GET(Endpoints.GET_CURRENT_WEATHER)
    suspend fun getCurrentWeather(): Response<CurrentWeatherResponse>

    /**
     * retrieves location weather status
     * @param locationId: [Int]
     * @return location weather: [LocationResponse]
     */
    @GET(Endpoints.GET_LOCATION_WEATHER)
    suspend fun getLocationWeather(
        @Path("id") locationId: Int
    ): Response<LocationResponse>
}