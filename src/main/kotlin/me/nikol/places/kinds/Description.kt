package me.nikol.places.kinds

import kotlinx.serialization.Serializable

@Serializable
data class Description(val name: String, val address: Address, val kinds: String) {
    fun beautifulToString(): String{
        return "$name \n${address.beautifulToString()} \nkinds: $kinds"
    }
}
