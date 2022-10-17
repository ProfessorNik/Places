package me.nikol.places

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.Json
import me.nikol.places.service.*

suspend fun main() = coroutineScope {
    try {
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
        configuration(client).forEach { it.handle(data) }
    } catch (e: Exception) {
        e.printStackTrace()
    }
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