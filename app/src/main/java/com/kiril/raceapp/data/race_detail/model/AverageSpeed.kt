package com.kiril.raceapp.data.race_detail.model


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AverageSpeed(
    val speed: String,
    val units: String
)