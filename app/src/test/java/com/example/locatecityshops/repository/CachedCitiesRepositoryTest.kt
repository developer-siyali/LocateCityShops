package com.example.locatecityshops.repository

import androidx.lifecycle.MutableLiveData
import com.example.locatecityshops.model.City
import com.example.locatecityshops.room.CitiesDao
import com.example.locatecityshops.room.CitiesEntity
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CachedCitiesRepositoryTest {
    private lateinit var citiesDao: CitiesDao
    private lateinit var cachedCitiesRepository: CachedCitiesRepository

    @Before
    fun setUp() {
        citiesDao = mockk()
    }

    @Test
    fun `assert and verify that the database data has been read in repository`() = runBlocking {
        // Given
        val cpt = City(0, "Cape Town", emptyList())
        val dbn = City(0, "Durban", emptyList())
        val jhb = City(0, "Johannesburg", emptyList())
        val cityList = listOf(cpt, dbn, jhb)
        val cityEntity = CitiesEntity(0, cityList)
        val cityEntityLiveData: MutableLiveData<CitiesEntity> = MutableLiveData()
        every { citiesDao.readCitiesData() } returns cityEntityLiveData
        coEvery { citiesDao.addCities(cityEntity) } just Runs

        // When
        cachedCitiesRepository = CachedCitiesRepository(citiesDao)

        // Then
        verify { citiesDao.readCitiesData() }
        assertEquals(cityEntityLiveData, citiesDao.readCitiesData())
    }

    @Test
    fun `verify that the addCities insert method was called in repository`() = runBlocking {
        // Given
        val cpt = City(0, "Cape Town", emptyList())
        val dbn = City(0, "Durban", emptyList())
        val jhb = City(0, "Johannesburg", emptyList())
        val cityList = listOf(cpt, dbn, jhb)
        val cityEntity = CitiesEntity(0, cityList)
        val cityEntityLiveData: MutableLiveData<CitiesEntity> = MutableLiveData()
        coEvery { citiesDao.addCities(cityEntity) } just Runs
        coEvery { citiesDao.readCitiesData() } returns cityEntityLiveData

        // When
        cachedCitiesRepository = CachedCitiesRepository(citiesDao)
        cachedCitiesRepository.addCities(cityEntity)


        // Then
        coVerify { citiesDao.addCities(cityEntity) }
    }
}