package com.mooncascade.data.mapper.observation

import com.mooncascade.data.entity.observation.ObservationEntity
import com.mooncascade.domain.model.current.Observation

fun Observation.toEntity() = ObservationEntity(
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
    wmocode = wmocode,
)

fun List<Observation>.toEntity(): List<ObservationEntity> = map { it.toEntity() }