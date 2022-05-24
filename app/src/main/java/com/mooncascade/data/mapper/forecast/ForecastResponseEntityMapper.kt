package com.mooncascade.data.mapper.forecast

import com.mooncascade.data.entity.forecast.DayNightEntity
import com.mooncascade.data.entity.forecast.ForecastEntity
import com.mooncascade.data.entity.forecast.PlaceEntity
import com.mooncascade.data.entity.forecast.WindEntity
import com.mooncascade.data.network.entity.forecast.DayNightResponse
import com.mooncascade.data.network.entity.forecast.ForecastResponse
import com.mooncascade.data.network.entity.forecast.PlaceResponse
import com.mooncascade.data.network.entity.forecast.WindResponse


fun ForecastResponse.toEntity() = ForecastEntity(
    date = date,
    day = day?.toEntity(),
    night = night?.toEntity(),
)

@JvmName("toEntityForecastResponse")
fun List<ForecastResponse>.toEntity(): List<ForecastEntity> = map { it.toEntity() }

fun DayNightResponse.toEntity() = DayNightEntity(
    peipsi = peipsi,
    phenomenon = phenomenon,
//    places = places?.toEntity(),
    sea = sea,
    tempmax = tempmax,
    tempmin = tempmin,
    text = text,
//    winds = winds?.toEntity()
)

fun WindResponse.toEntity() = WindEntity(
    direction = direction,
    name = name,
    speedmax = speedmax,
    speedmin = speedmin
)

@JvmName("toEntityWindResponse")
fun List<WindResponse>.toEntity(): List<WindEntity> = map { it.toEntity() }

fun PlaceResponse.toEntity() = PlaceEntity(
    name = name,
    phenomenon = phenomenon,
    tempmax = tempmax,
    tempmin = tempmin
)

@JvmName("toEntityPlaceResponse")
fun List<PlaceResponse>.toEntity() = map { it.toEntity() }