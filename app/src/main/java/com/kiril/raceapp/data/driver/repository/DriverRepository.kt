package com.kiril.raceapp.data.driver.repository

import com.kiril.raceapp.data.driver.model.DriverDetail
import com.kiril.raceapp.data.network.ErgastApi
import com.skydoves.sandwich.ApiResponse

class DriverRepository(private val ergastApi: ErgastApi) {
    suspend fun getDriverByName(name: String): ApiResponse<DriverDetail> {
        return ergastApi.getDriverByName(name)
    }
}