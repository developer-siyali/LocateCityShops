package com.example.locatecityshops

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.locatecityshops.api.CityApiImpl
import com.example.locatecityshops.databinding.ActivityMainBinding
import com.example.locatecityshops.repository.CitiesRepository
import com.example.locatecityshops.room.CitiesEntity
import com.example.locatecityshops.util.Util
import com.example.locatecityshops.viewmodel.CachedCitiesViewModel
import com.example.locatecityshops.viewmodel.CitiesViewModel
import com.example.locatecityshops.viewmodel.CitiesViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CitiesViewModel
    private lateinit var cachedCitiesViewModel: CachedCitiesViewModel
    private lateinit var repository: CitiesRepository
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = CitiesRepository()
        val viewModelFactory = CitiesViewModelFactory(repository)
        viewModel = ViewModelProvider(this@MainActivity, viewModelFactory).get(CitiesViewModel::class.java)
        cachedCitiesViewModel = ViewModelProvider(this@MainActivity).get(CachedCitiesViewModel::class.java)
        viewModel.setConnectivityIndicator(Util().isNetworkAvailable(this))
        if (Util().isNetworkAvailable(this)) {
            viewModel.getCities()
            viewModel.citiesApiResponse.observe(this@MainActivity, Observer { response ->
                if (response.isSuccessful) {
                    response.body()?.cities?.let {
                        viewModel.setCites(it)
                        cachedCitiesViewModel.addCities(CitiesEntity(0, it))
                        cachedCitiesViewModel.readAllData.observe(this, Observer { cities ->
                            viewModel.setCites(cities.cities)
                        })
                    }
                } else {
                    cachedCitiesViewModel.readAllData.observe(this@MainActivity, Observer { cities ->
                        viewModel.setCites(cities.cities)
                    })
                }
            })
        } else {
            cachedCitiesViewModel.readAllData.observe(this@MainActivity, Observer { cities ->
                viewModel.setCites(cities.cities)
            })
        }
    }
}