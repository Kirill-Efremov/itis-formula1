package com.kiril.raceapp.data.race.model


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Qualifying(
    val date: String,
    val time: String
)