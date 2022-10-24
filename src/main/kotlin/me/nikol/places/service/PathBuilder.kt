package me.nikol.places.service

import me.nikol.places.interestingplace.InterestingPlace
import me.nikol.places.placesandcountries.Place

interface PathBuilder {
    fun build() : String
}

class LocationsPathBuilder (val placeName: String, val key: String = ApplicationProperties.getProperty("graphhopper.key")) : PathBuilder{
    override fun build(): String {
        return "https://graphhopper.com/api/1/geocode?q=$placeName&key=$key".replace(" ", "%20")
    }
}

class WeatherPathBuilder (val place: Place, val key: String = ApplicationProperties.getProperty("openweathermap.key")) : PathBuilder {
    override fun build(): String {
        return "https://api.openweathermap.org/data/2.5/weather?" +
                "lat=${place.point!!.lat}" +
                "&lon=${place.point.lng}" +
                "&appid=$key"
    }
}

class InterestingPlacesPathBuilder(
    val place: Place,
    val key: String = ApplicationProperties.getProperty("opentripmap.key"),
    val radius: Double = ApplicationProperties.getProperty("opentripmap.radius").toDouble()) : PathBuilder {
    override fun build(): String {
        return "https://api.opentripmap.com/0.1/en/places/radius?apikey=$key&radius=$radius&lon=${place.point!!.lng}&lat=${place.point.lat}"
    }
}

class PlaceDescriptionPathBuilder(val interestingPlace: InterestingPlace, val key: String = ApplicationProperties.getProperty("opentripmap.key")) : PathBuilder{
    override fun build(): String {
        return "https://api.opentripmap.com/0.1/en/places/xid/${interestingPlace.xid}?apikey=$key"
    }

}