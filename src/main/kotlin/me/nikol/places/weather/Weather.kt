package me.nikol.places.weather

import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    val weather: List<WeatherIntro>? = null,
    val main: MainWeather? = null,
    val wind: Wind? = null,
    val clouds: Clouds? = null,
)