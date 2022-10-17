package me.nikol.places.interestingplace

import kotlinx.serialization.Serializable

@Serializable
data class InterestingPlaces(
    val features: List<Features>
)
