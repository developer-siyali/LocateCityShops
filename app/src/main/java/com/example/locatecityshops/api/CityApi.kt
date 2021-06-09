package com.example.locatecityshops.api

import com.example.locatecityshops.model.Cities
import retrofit2.Response
import retrofit2.http.GET

interface CityApi {

    @GET("/v2/5b7e8bc03000005c0084c210")
    suspend fun getCities(): Response<Cities>
}