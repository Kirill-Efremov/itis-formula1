package com.kiril.raceapp.data.network

import com.kiril.raceapp.data.race.model.RaceResponse
import com.kiril.raceapp.data.standings.model.StandingsResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface ErgastApi {
    @GET("f1/current.json")
    suspend fun getAllRaces(): ApiResponse<RaceResponse>
    @GET("f1/current/driverStandings.json")
    suspend fun getDriverStandings(): ApiResponse<StandingsResponse>
}