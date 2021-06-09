package com.example.locatecityshops.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.locatecityshops.model.City

@Dao
interface CitiesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCities(cities: CitiesEntity)

    @Query("SELECT * FROM cities_table ORDER BY id ASC")
    fun readCitiesData(): LiveData<CitiesEntity>
}