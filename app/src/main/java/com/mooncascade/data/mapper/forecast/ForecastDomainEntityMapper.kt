package com.mooncascade.data.mapper.forecast

import com.mooncascade.data.entity.forecast.DayNightEntity
import com.mooncascade.data.entity.forecast.ForecastEntity
import com.mooncascade.data.entity.forecast.PlaceEntity
import com.mooncascade.data.entity.forecast.WindEntity
import com.mooncascade.domain.model.forecast.DayNight
import com.mooncascade.domain.model.forecast.Forecast
import com.mooncascade.domain.model.forecast.Place
import com.mooncascade.domain.model.forecast.Wind


fun Forecast.toEntity() = ForecastEntity(
    date = date,
    day = day?.toEntity(),
    night = night?.toEntity(),
)

@JvmName("toEntityForecastDomain")
fun List<Forecast>.toEntity(): List<ForecastEntity> = map { it.toEntity() }

fun DayNight.toEntity() = DayNightEntity(
    peipsi = peipsi,
    phenomenon = phenomenon,
//    places = places?.toEntity(),
    sea = sea,
    tempmax = tempmax,
    tempmin = tempmin,
    text = text,
//    winds = winds?.toEntity()
)

fun Wind.toEntity() = WindEntity(
    direction = direction,
    name = name,
    speedmax = speedmax,
    speedmin = speedmin
)

@JvmName("toEntityWindDomain")
fun List<Wind>.toEntity(): List<WindEntity> = map { it.toEntity() }

fun Place.toEntity() = PlaceEntity(
    name = name,
    phenomenon = phenomenon,
    tempmax = tempmax,
    tempmin = tempmin
)

@JvmName("toEntityPlaceDomain")
fun List<Place>.toEntity() = map { it.toEntity() }