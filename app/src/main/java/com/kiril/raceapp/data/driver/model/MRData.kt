package com.kiril.raceapp.data.driver.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MRData(
    @Json(name = "DriverTable")
    val driverTable: DriverTable,
    val limit: String,
    val offset: String,
    val series: String,
    val total: String,
    val url: String,
    val xmlns: String
)