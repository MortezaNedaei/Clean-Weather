package com.mooncascade.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import androidx.annotation.RawRes
import com.mooncascade.R

/**
 * Used to represent the weather type with description and icon
 */
@Keep
enum class WeatherType(
    val weatherName: String,
    val weatherDescription: String,
    @DrawableRes val weatherIcon: Int,
    @RawRes val weatherAnim: Int
) {
    CLEAR("Clear", "Clear", R.drawable.ic_clear, R.raw.clear_day),
    CLOUDY("Cloudy", "Cloudy sky or Overcast", R.drawable.ic_cloudy, R.raw.mostly_cloudy),
    SUNNY_CLOUDY("Partly Cloudy", "Variable clouds", R.drawable.ic_sunny_coudy, R.raw.few_clouds),
    RAIN("Rain", "Light shower Rain", R.drawable.ic_rainy, R.raw.rainy_weather),
    SNOW("Snow", "Snow", R.drawable.ic_snowy, R.raw.snow_weather),
    STORM("Storm", "Storm or Windy", R.drawable.ic_storm, R.raw.storm_weather),
    THUNDERSTORM(
        "Thunderstorm",
        "Thunderstorm or Windy",
        R.drawable.ic_thunderstorm,
        R.raw.storm_weather
    ),
    UNKNOWN("Unknown", "Unknown", R.drawable.ic_unknown_weather, R.raw.unknown_weather);
}