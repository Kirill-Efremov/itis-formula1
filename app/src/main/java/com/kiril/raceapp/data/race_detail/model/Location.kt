package com.kiril.raceapp.data.race_detail.model


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(
    val country: String,
    val lat: String,
    val locality: String,
    val long: String
)