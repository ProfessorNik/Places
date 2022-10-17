package me.nikol.places.weather

import kotlinx.serialization.Serializable

@Serializable
data class WeatherIntro(
    val main: String? = null,
    val description: String? = null
)
