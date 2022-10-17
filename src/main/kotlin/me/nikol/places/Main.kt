package me.nikol.places

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import me.nikol.places.interestingplace.InterestingPlace
import me.nikol.places.interestingplace.InterestingPlaces
import me.nikol.places.placesandcountries.Locations
import me.nikol.places.placesandcountries.Place
import me.nikol.places.weather.Weather
import java.util.*
import kotlinx.coroutines.*
import me.nikol.places.kinds.Description

suspend fun main() = coroutineScope {
    val client = HttpClient(CIO) {
        expectSuccess = true

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }

    println("Input place name:")
    val locations = getLocations(client, readPlaceNameFromCli(), "71e9e299-d7f2-45a7-86b7-d48388c40e80")
    for (i in locations.hits.indices) {
        println("$i : ${locations.hits[i].beautifulToString()}")
    }
    println("Input number [0, ${locations.hits.size})")

    val place = readPlace(locations)
    val weatherDef = async {
        weather(client, place, "38388be4fa3867e6b58fd98f0374606c")
    }
    val interestingPlacesDef = async {
        interestingPlaces(client, place, "5ae2e3f221c38a28845f05b61a6692096bd4e65d5a1561da2561305d", 100.0)
    }

    println("Weather: ${weatherDef.await().weather!![0].description}, temp: ${weatherDef.await().main?.temp}")
    val interestingPlaces = interestingPlacesDef.await()

    println("Interesting places: ")
    for (i in interestingPlaces.features.indices) {
        println("$i : name=${interestingPlaces.features[i].properties!!.name}, \n\t description=${interestingPlaces.features[i].properties!!.kinds}")
    }

    while (true) {
        println("Input number [0, ${interestingPlaces.features.size}) for choice or else for exit")
        try {
            println(
                interestingPlaceKind(
                    client,
                    readChoiceElementOfList(interestingPlaces.features).properties!!,
                    "5ae2e3f221c38a28845f05b61a6692096bd4e65d5a1561da2561305d"
                ).beautifulToString()
            )
        } catch (e: InputMismatchException) {
            return@coroutineScope
        }
    }
}

suspend fun getLocations(client: HttpClient, placeName: String, key: String): Locations {
    return client.get(locationsPath(placeName, key)).body()
}

suspend fun weather(client: HttpClient, place: Place, key: String): Weather {
    return client.get(weatherPath(place, key)).body()
}

suspend fun interestingPlaces(
    client: HttpClient,
    place: Place,
    key: String,
    radius: Double = 28270.0
): InterestingPlaces {
    return client.get(interestingPlacesPath(place, key, radius)).body()
}

suspend fun interestingPlaceKind(client: HttpClient, interestingPlace: InterestingPlace, key: String): Description {
    return client.get(interestingPlacesKindPath(interestingPlace, key)).body()
}

fun readPlaceNameFromCli(): String {
    return Scanner(System.`in`).nextLine()
}

fun readNextIntFromCli(): Int {
    return Scanner(System.`in`).nextInt()
}

fun readPlace(locations: Locations): Place {
    when (val placeNumber = readNextIntFromCli()) {
        in 0 until locations.hits.size -> {
            return locations.hits[placeNumber]
        }
        else -> {
            throw InputMismatchException("Value must be [0, ${locations.hits.size})")
        }
    }
}

fun <E> readChoiceElementOfList(list: List<E>): E {
    when (val choice = readNextIntFromCli()) {
        in list.indices -> {
            return list[choice]
        }
        else -> {
            throw InputMismatchException("Value must be [0, ${list.size})")
        }
    }
}


fun locationsPath(placeName: String, key: String): String {
    return "https://graphhopper.com/api/1/geocode?q=$placeName&key=$key".replace(" ", "%20")
}

fun weatherPath(place: Place, key: String): String {
    return "https://api.openweathermap.org/data/2.5/weather?" +
            "lat=${place.point!!.lat}" +
            "&lon=${place.point.lng}" +
            "&appid=$key"
}


fun interestingPlacesPath(
    place: Place,
    key: String = "5ae2e3f221c38a28845f05b61a6692096bd4e65d5a1561da2561305d",
    radius: Double
): String {
    return "https://api.opentripmap.com/0.1/en/places/radius?apikey=$key&radius=$radius&lon=${place.point!!.lng}&lat=${place.point.lat}"
}

fun interestingPlacesKindPath(interestingPlace: InterestingPlace, key: String): String {
    return "https://api.opentripmap.com/0.1/en/places/xid/${interestingPlace.xid}?apikey=$key"
}