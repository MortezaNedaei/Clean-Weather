package com.mooncascade.data.mapper.forecast

import com.mooncascade.data.entity.forecast.DayNightEntity
import com.mooncascade.data.entity.forecast.ForecastEntity
import com.mooncascade.data.entity.forecast.PlaceEntity
import com.mooncascade.data.entity.forecast.WindEntity
import com.mooncascade.domain.model.forecast.DayNight
import com.mooncascade.domain.model.forecast.Forecast
import com.mooncascade.domain.model.forecast.Place
import com.mooncascade.domain.model.forecast.Wind


fun ForecastEntity.toDomain() = Forecast(
    date = date,
    day = day?.toDomain(),
    night = night?.toDomain(),
)

@JvmName("toDomainForecastEntity")
fun List<ForecastEntity>.toDomain(): List<Forecast> = map { it.toDomain() }

fun DayNightEntity.toDomain() = DayNight(
    peipsi = peipsi,
    phenomenon = phenomenon,
    places = emptyList(),
//    places = places?.toDomain(),
    sea = sea,
    tempmax = tempmax,
    tempmin = tempmin,
    text = text,
    winds = emptyList()
//    winds = winds?.toDomain()
)

fun WindEntity.toDomain() = Wind(
    direction = direction,
    name = name,
    speedmax = speedmax,
    speedmin = speedmin
)

@JvmName("toDomainWindEntity")
fun List<WindEntity>.toDomain(): List<Wind> = map { it.toDomain() }

fun PlaceEntity.toDomain() = Place(
    id = 0,
    name = name,
    phenomenon = phenomenon,
    tempmax = tempmax,
    tempmin = tempmin
)

@JvmName("toDomainPlaceEntity")
fun List<PlaceEntity>.toDomain() = map { it.toDomain() }