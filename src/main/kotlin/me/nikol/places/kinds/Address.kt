package me.nikol.places.kinds

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val city: String,
    val road: String,
    val state: String,
    val suburb: String,
    val country: String,
    val postcode: String,
    val country_code: String,
    val house_number: String,
    val city_district: String,
    val neighbourhood: String
) {
    fun beautifulToString(): String{
        return "address: $city, $road, $state, $city_district, $country, $neighbourhood, $house_number, $postcode"
    }
}