package me.nikol.places.kinds

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val city: String? = null,
    val road: String? = null,
    val state: String? = null,
    val suburb: String? = null,
    val country: String? = null,
    val postcode: String? = null,
    val country_code: String? = null,
    val house_number: String? = null,
    val city_district: String? = null,
    val neighbourhood: String? = null
) {
    fun beautifulToString(): String{
        return "address: $city, $road, $state, $city_district, $country, $neighbourhood, $house_number, $postcode"
    }
}