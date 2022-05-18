package com.mooncascade.data.network

import com.mooncascade.data.entity.current.CurrentWeatherEntity
import com.mooncascade.data.entity.forecast.NextDaysForecastEntity
import com.mooncascade.data.entity.forecast.ForecastEntity
import com.mooncascade.data.entity.location.LocationEntity
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
        const val GET_RANDOM_IMAGE = "https://picsum.photos/seed/720/720"
    }

    /**
     * retrieves forecasts for the next 4 days
     * @return forecasts: [ForecastEntity]
     */
    @GET(Endpoints.GET_FORECASTS)
    suspend fun getNextDaysForecasts(): Response<NextDaysForecastEntity>

    /**
     * retrieves current weather status
     * @return current weather: [CurrentWeatherEntity]
     */
    @GET(Endpoints.GET_CURRENT_WEATHER)
    suspend fun getCurrentWeather(): Response<CurrentWeatherEntity>

    /**
     * retrieves location weather status
     * @param locationId: [Int]
     * @return location weather: [LocationEntity]
     */
    @GET(Endpoints.GET_LOCATION_WEATHER)
    suspend fun getLocationWeather(
        @Path("id") locationId: Int
    ): Response<LocationEntity>
}