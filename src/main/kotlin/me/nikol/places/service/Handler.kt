package me.nikol.places.service

import io.ktor.client.*
import kotlinx.coroutines.*
import me.nikol.places.kinds.Description
import me.nikol.places.placesandcountries.Locations
import me.nikol.places.placesandcountries.Place
import java.util.*

interface Handler {
    suspend fun handle(data: ResponseData)
}

class GetLocationsHandler(private val client: HttpClient) : Handler {
    override suspend fun handle(data: ResponseData) {
        val locations: Locations = RequestExecutor(client, LocationsPathBuilder(readPlaceNameFromCli())).execute()

        printLocations(locations)
        data.place = readLocationChoice(locations)
    }

    private fun readPlaceNameFromCli(): String {
        println("Input place name:")
        return Scanner(System.`in`).nextLine()
    }

    private fun printLocations(locations: Locations) {
        for (i in locations.hits.indices) {
            println("$i : ${locations.hits[i].beautifulToString()}")
        }
    }

    private fun readLocationChoice(locations: Locations): Place {
        println("Input number [0, ${locations.hits.size})")
        return readChoiceElementOfList(locations.hits)
    }
}

class GetWeatherHandler(private val client: HttpClient) : Handler {
    override suspend fun handle(data: ResponseData) = coroutineScope {
        data.weatherDeferred = async {
            RequestExecutor(client, WeatherPathBuilder(data.place)).execute()
        }
    }
}

class GetInterestingPlacesHandler(private val client: HttpClient) : Handler {
    override suspend fun handle(data: ResponseData) = coroutineScope {
        data.interestingPlacesDeferred = async {
            RequestExecutor(client, InterestingPlacesPathBuilder(data.place)).execute()
        }
    }
}

class PrintWeatherHandler() : Handler {
    override suspend fun handle(data: ResponseData) {
        val weather = data.weatherDeferred.await()
        println("Weather: ${weather.weather!![0].description}, temp: ${weather.main?.temp}")
    }
}

class PrintInterestingLocations() : Handler {
    override suspend fun handle(data: ResponseData) {
        val interestingPlaces = data.interestingPlacesDeferred.await()

        println("Interesting places: ")
        for (i in interestingPlaces.features.indices) {
            println("$i : name=${interestingPlaces.features[i].properties!!.name}, \n\t description=${interestingPlaces.features[i].properties!!.kinds}")
        }
    }
}

class GetDescriptions(private val client: HttpClient) : Handler {
    override suspend fun handle(data: ResponseData) {
        val interestingPlaces = data.interestingPlacesDeferred.await()

        while (true) {
            println("Input number [0, ${interestingPlaces.features.size}) for choice or else for exit")
            try {
                println(
                    RequestExecutor(
                        client, PlaceDescriptionPathBuilder(
                            readChoiceElementOfList(interestingPlaces.features).properties!!
                        )
                    ).execute<Description>()
                        .beautifulToString()
                )
            } catch (e: InputMismatchException) {
                return
            }
        }
    }
}

fun <E> readChoiceElementOfList(list: List<E>): E {
    when (val choice = Scanner(System.`in`).nextInt()) {
        in list.indices -> {
            return list[choice]
        }
        else -> {
            throw InputMismatchException("Value must be [0, ${list.size})")
        }
    }
}

