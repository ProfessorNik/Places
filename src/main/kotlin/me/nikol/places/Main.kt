package me.nikol.places

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.util.*

suspend fun main() {
    val client = HttpClient(CIO)
    val response = client.get("https://ktor.io")
    println("Hello, https!")
    println(response.status)
}