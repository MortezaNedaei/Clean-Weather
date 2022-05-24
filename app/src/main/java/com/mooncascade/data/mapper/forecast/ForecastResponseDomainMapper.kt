package com.mooncascade.data.mapper.forecast

import com.mooncascade.data.network.entity.forecast.*
import com.mooncascade.domain.model.forecast.*

fun NextDaysForecastResponse.toDomain() = NextDaysForecast(
    forecasts = forecasts?.toDomain()
)

fun ForecastResponse.toDomain() = Forecast(
    date = date,
    day = day?.toDomain(),
    night = night?.toDomain(),
)

@JvmName("toDomainForecastResponse")
fun List<ForecastResponse>.toDomain(): List<Forecast> = map { it.toDomain() }

fun DayNightResponse.toDomain() = DayNight(
    peipsi = peipsi,
    phenomenon = phenomenon,
    places = places?.toDomain(),
    sea = sea,
    tempmax = tempmax,
    tempmin = tempmin,
    text = text,
    winds = winds?.toDomain()
)

fun WindResponse.toDomain() = Wind(
    direction = direction,
    name = name,
    speedmax = speedmax,
    speedmin = speedmin
)

@JvmName("toDomainWindResponse")
fun List<WindResponse>.toDomain(): List<Wind> = map { it.toDomain() }

fun PlaceResponse.toDomain() = Place(
    id = 0,
    name = name,
    phenomenon = phenomenon,
    tempmax = tempmax,
    tempmin = tempmin
)

@JvmName("toDomainPlaceResponse")
fun List<PlaceResponse>.toDomain() = map { it.toDomain() }