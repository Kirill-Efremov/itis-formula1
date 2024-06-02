package com.kiril.raceapp.data.driver.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DriverDetail(
    @Json(name = "MRData")
    val mRData: MRData
)