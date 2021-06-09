package com.example.locatecityshops.repository

import android.accounts.Account
import com.example.locatecityshops.api.CityApi
import com.example.locatecityshops.model.Cities
import com.example.locatecityshops.model.City
import com.example.locatecityshops.room.CitiesEntity
import com.google.gson.Gson
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.IsInstanceOf.any
import org.junit.Before
import org.junit.Test
import retrofit2.Response


class CitiesRepositoryTest {

    private lateinit var cityApi: CityApi
    private lateinit var citiesRepository: CitiesRepository
    private val cpt = City(0, "Cape Town", emptyList())
    private val dbn = City(0, "Durban", emptyList())
    private val jhb = City(0, "Johannesburg", emptyList())
    private val cityList = listOf(cpt, dbn, jhb)
    private val cities = Cities(cityList)

    @Before
    fun setUp() {
        cityApi = mockk()
    }

    @Test
    fun `check if the api getCities method is called in the repository`() = runBlocking {
        //Given
        val response = mockk<Response<Cities>>()
        every { response.body() } returns cities
        coEvery { cityApi.getCities() } returns response

        //When
        citiesRepository = CitiesRepository(cityApi)
        citiesRepository.getCities()

        //Then
        coVerify { cityApi.getCities() }

    }

}