package com.example.chargingstationsfinder.network

import com.example.chargingstationsfinder.network.dtos.StationsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    @GET("poi/?output=json&maxresults=100&compact=true&verbose=false")
    suspend fun fetchStations(
        @Query("key") key: String,
        @Query("countrycode")countryCode: String
    ): Response<StationsResponseDto>


}