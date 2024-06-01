package com.kiril.raceapp.data.race_detail.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FastestLap(
    @Json(name = "AverageSpeed")
    val averageSpeed: AverageSpeed,
    val lap: String,
    val rank: String,
    @Json(name = "Time")
    val time: Time
)