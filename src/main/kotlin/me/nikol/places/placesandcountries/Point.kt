package me.nikol.places.placesandcountries

import kotlinx.serialization.Serializable

@Serializable
data class Point(val lat: Double, val lng: Double)