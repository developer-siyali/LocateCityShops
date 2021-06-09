package com.example.locatecityshops.api

import com.example.locatecityshops.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CityApiImpl {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: CityApi by lazy {
        retrofit.create(CityApi::class.java)
    }
}