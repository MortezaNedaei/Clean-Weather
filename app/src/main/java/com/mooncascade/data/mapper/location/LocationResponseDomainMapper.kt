package com.mooncascade.data.mapper

import com.mooncascade.data.network.entity.location.*
import com.mooncascade.domain.model.location.*

fun LocationResponse.toDomain() = Location(
    cloudbase = cloudbase,
    cloudcoverlow = cloudcoverlow,
    cloudcovertotal = cloudcovertotal,
    cloudtypehigh = cloudtypehigh,
    cloudtypelow = cloudcoverlow,
    cloudtypemed = cloudtypemed,
    code = code,
    dewpoint = dewpoint?.toDomain(),
    mode = mode,
    nameEng = nameEng,
    nameEst = nameEst,
    precipitations = precipitations?.toDomain(),
    precipitationsperiod = precipitationsperiod?.toDomain(),
    presentweather = presentweather,
    pressure = pressure?.toDomain(),
    pressurechange = pressurechange?.toDomain(),
    pressuretendency = pressuretendency,
    relativehumidity = relativehumidity?.toDomain(),
    temperature = temperature?.toDomain(),
    visibility = visibility,
    wind = wind?.toDomain(),
    winddirection = winddirection?.toDomain(),
)

fun DewpointResponse.toDomain() = Dewpoint(
    units = units,
    value = value
)

fun PrecipitationsResponse.toDomain() = Precipitations(
    units = units,
    value = value
)

fun PrecipitationsperiodResponse.toDomain() = Precipitationsperiod(
    units = units,
    value = value
)

fun PressureResponse.toDomain() = Pressure(
    units = units,
    value = value
)

fun PressurechangeResponse.toDomain() = Pressurechange(
    units = units,
    value = value
)

fun RelativehumidityResponse.toDomain() = Relativehumidity(
    units = units,
    value = value
)

fun TemperatureResponse.toDomain() = Temperature(
    units = units,
    value = value
)


fun WindResponse.toDomain() = Wind(
    units = units,
    value = value
)


fun WinddirectionResponse.toDomain() = Winddirection(
    units = units,
    value = value
)
