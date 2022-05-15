package com.mooncascade.data.network

import com.mooncascade.data.entity.ForecastDataEntity
import com.mooncascade.data.entity.ForecastEntity
import retrofit2.Response
import retrofit2.http.GET

interface WeatherApi {

    /**
     * used to hold the API endpoint constants
     */
    object Endpoints {
        const val GET_FORECASTS = "estonia/forecast/"
    }

    /**
     * retrieves forecasts for the next 4 days
     * @return forecasts: [ForecastEntity] [List]
     */
    @GET(Endpoints.GET_FORECASTS)
    suspend fun getForecasts(): Response<ForecastDataEntity>
}