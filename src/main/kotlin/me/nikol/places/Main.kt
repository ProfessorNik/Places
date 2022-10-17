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
import me.nikol.places.service.*

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
    
    val data = ResponseData()
    configuration(client).forEach{it.handle(data)}
}


fun configuration(client: HttpClient): List<Handler> {
    return listOf(
        GetLocationsHandler(client),
        GetWeatherHandler(client),
        GetInterestingPlacesHandler(client),
        PrintWeatherHandler(),
        PrintInterestingLocations(),
        GetDescriptions(client)
    )
}