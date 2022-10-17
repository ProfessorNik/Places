package me.nikol.places.placesandcountries

import kotlinx.serialization.Serializable

@Serializable
data class Place(val point: Point? = null,
                 val name: String? = null,
                 val country: String? = null,
                 val city: String? = null,
                 val state: String? = null,
                 val street: String? = null,
                 val housenumber: String? = null,
                 val postcode: String? = null) {
    fun beautifulToString(): String{
        var string = "";
        name?.let {
            string += "name=\"$it\" "
        }
        country?.let {
            string += "country=\"$it\" "
        }
        city?.let {
            string += "city=\"$it\" "
        }
        state?.let {
            string += "state=\"$it\" "
        }
        street?.let {
            string += "street=\"$it\" "
        }
        housenumber?.let {
            string += "house number=\"$it\" "
        }
        postcode?.let {
            string += "postcode=\"$it\" "
        }
        point?.let {
            string+="coords=\"$it\" "
        }
        return string
    }
}
