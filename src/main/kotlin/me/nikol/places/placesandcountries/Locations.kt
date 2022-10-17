package me.nikol.places.placesandcountries

import kotlinx.serialization.Serializable

@Serializable
data class Locations(val hits: List<Place>)