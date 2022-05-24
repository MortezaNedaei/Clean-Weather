package com.mooncascade.data.mapper

import com.mooncascade.data.network.entity.current.CurrentWeatherResponse
import com.mooncascade.data.network.entity.current.ObservationResponse
import com.mooncascade.domain.model.current.CurrentWeather
import com.mooncascade.domain.model.current.Observation

fun CurrentWeatherResponse.toDomain(): CurrentWeather = CurrentWeather(
    timestamp = timestamp,
    observations = observations.toDomain()
)

fun ObservationResponse.toDomain(): Observation = Observation(
    airpressure = airpressure,
    airtemperature = airtemperature,
    latitude = latitude,
    longitude = longitude,
    name = name,
    phenomenon = phenomenon,
    precipitations = precipitations,
    relativehumidity = relativehumidity,
    uvindex = uvindex,
    visibility = visibility,
    waterlevel = waterlevel,
    waterlevelEh2000 = waterlevelEh2000,
    watertemperature = watertemperature,
    winddirection = winddirection,
    windspeed = windspeed,
    windspeedmax = windspeedmax,
    wmocode = wmocode
)

fun List<ObservationResponse>.toDomain(): List<Observation> = map { it.toDomain() }