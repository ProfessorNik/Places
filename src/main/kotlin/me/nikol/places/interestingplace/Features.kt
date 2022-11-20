package me.nikol.places.interestingplace

import kotlinx.serialization.Serializable

@Serializable
data class Features(val properties: InterestingPlace?=null)
