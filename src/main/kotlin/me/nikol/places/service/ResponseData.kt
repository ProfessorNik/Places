package me.nikol.places.service

import kotlinx.coroutines.Deferred
import me.nikol.places.interestingplace.InterestingPlaces
import me.nikol.places.placesandcountries.Locations
import me.nikol.places.placesandcountries.Place
import me.nikol.places.weather.Weather

class ResponseData{
    lateinit var place: Place
    lateinit var weatherDeferred: Deferred<Weather>
    lateinit var interestingPlacesDeferred: Deferred<InterestingPlaces>
}