package com.example.locatecityshops.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.locatecityshops.repository.CachedCitiesRepository
import com.example.locatecityshops.room.CitiesDatabase
import com.example.locatecityshops.room.CitiesEntity
import kotlinx.coroutines.launch

class CachedCitiesViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<CitiesEntity>
    private val repository: CachedCitiesRepository

    init {
        val citiesDatabase = CitiesDatabase.getDatabase(application).citiesDao()
        repository = CachedCitiesRepository(citiesDatabase)
        readAllData = repository.readAllData
    }

    fun addCities(cities: CitiesEntity) {
        viewModelScope.launch {
            repository.addCities(cities)
        }
    }
}