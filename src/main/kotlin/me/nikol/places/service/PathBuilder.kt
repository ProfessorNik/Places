package me.nikol.places.service

import me.nikol.places.interestingplace.InterestingPlace
import me.nikol.places.placesandcountries.Place

interface PathBuilder {
    fun build() : String
}

class LocationsPathBuilder (val placeName: String, val key: String = "71e9e299-d7f2-45a7-86b7-d48388c40e80") : PathBuilder{
    override fun build(): String {
        return "https://graphhopper.com/api/1/geocode?q=$placeName&key=$key".replace(" ", "%20")
    }
}

class WeatherPathBuilder (val place: Place, val key: String = "38388be4fa3867e6b58fd98f0374606c") : PathBuilder {
    override fun build(): String {
        return "https://api.openweathermap.org/data/2.5/weather?" +
                "lat=${place.point!!.lat}" +
                "&lon=${place.point.lng}" +
                "&appid=$key"
    }
}

class InterestingPlacesPathBuilder(
    val place: Place,
    val key: String = "5ae2e3f221c38a28845f05b61a6692096bd4e65d5a1561da2561305d",
    val radius: Double = 100.0) : PathBuilder {
    override fun build(): String {
        return "https://api.opentripmap.com/0.1/en/places/radius?apikey=$key&radius=$radius&lon=${place.point!!.lng}&lat=${place.point.lat}"
    }
}

class PlaceDescriptionPathBuilder(val interestingPlace: InterestingPlace, val key: String = "5ae2e3f221c38a28845f05b61a6692096bd4e65d5a1561da2561305d") : PathBuilder{
    override fun build(): String {
        return "https://api.opentripmap.com/0.1/en/places/xid/${interestingPlace.xid}?apikey=$key"
    }

}