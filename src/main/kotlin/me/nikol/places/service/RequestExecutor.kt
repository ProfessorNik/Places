package me.nikol.places.service

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class RequestExecutor(val client: HttpClient, var pathBuilder: PathBuilder) {
    suspend inline fun <reified T> execute(): T = client.get(pathBuilder.build()).body()
}