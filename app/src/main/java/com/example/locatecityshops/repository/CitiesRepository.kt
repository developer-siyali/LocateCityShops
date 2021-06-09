package com.example.locatecityshops.repository

import com.example.locatecityshops.api.CityApi
import com.example.locatecityshops.api.CityApiImpl
import com.example.locatecityshops.model.Cities
import retrofit2.Response

class CitiesRepository(private val cityApi: CityApi = CityApiImpl().api) {

    suspend fun getCities(): Response<Cities> {
        return cityApi.getCities()
    }
}