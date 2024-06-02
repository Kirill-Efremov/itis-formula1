package com.kiril.raceapp.data.driver.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DriverTable(
    val driverId: String,
    @Json(name = "Drivers")
    val drivers: List<Driver>
)