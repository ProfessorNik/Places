package me.nikol.places.interestingplace

import kotlinx.serialization.Serializable

@Serializable
data class InterestingPlace(
    val xid: String,
    val name: String,
    val kinds: String
)
