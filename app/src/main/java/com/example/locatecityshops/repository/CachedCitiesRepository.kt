package com.example.locatecityshops.repository

import androidx.lifecycle.LiveData
import com.example.locatecityshops.room.CitiesDao
import com.example.locatecityshops.room.CitiesEntity

class CachedCitiesRepository(private val citiesDao: CitiesDao) {

    var readAllData: LiveData<CitiesEntity> = citiesDao.readCitiesData()

    suspend fun addCities(cities: CitiesEntity) {
        citiesDao.addCities(cities)
    }
}