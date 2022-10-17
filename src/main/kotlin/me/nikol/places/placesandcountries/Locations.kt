package me.nikol.places.placesandcountries

import kotlinx.serialization.*

@Serializable
data class Locations(val hits: List<Place>) {

}