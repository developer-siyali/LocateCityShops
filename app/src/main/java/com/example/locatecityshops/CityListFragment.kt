package com.example.locatecityshops

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.locatecityshops.api.CityApiImpl
import com.example.locatecityshops.recycler_view.DisplayLocationListFragment
import com.example.locatecityshops.recycler_view.RecyclerRow
import com.example.locatecityshops.repository.CitiesRepository
import com.example.locatecityshops.viewmodel.CitiesViewModel
import com.example.locatecityshops.viewmodel.CitiesViewModelFactory

class CitiesListDisplayFragment : DisplayLocationListFragment() {
    private var cities: List<RecyclerRow>? = null
    private lateinit var viewModel: CitiesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.setSearchInstructionEntity(resources.getString(R.string.cities))
        val viewModelFactory = CitiesViewModelFactory(CitiesRepository())
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(CitiesViewModel::class.java)
        viewModel.isConnected.observe(viewLifecycleOwner, Observer { isConnected ->
            viewModel.citiesList.observe(viewLifecycleOwner, Observer { responseCities ->
                if (isConnected || (!isConnected && responseCities != null)) {
                    if (!isConnected) {
                            Toast.makeText(context?.applicationContext, "The was internet connection found, previously save information is displayed", Toast.LENGTH_LONG)
                                .show()
                    }
                    cities = responseCities.cities.map { city ->
                        RecyclerRow(city.name, city.malls.size, resources.getString(R.string.mall))
                    }
                    cities?.toTypedArray()?.let { cityList -> this.submitList(cityList) }
                } else {
                    Navigation.findNavController(view).navigate(R.id.citiesListFragmentToNoDataAlertFragment)
                }
            })
        })
    }

    override fun onItemClicked(positionClickedIndex: Int) {
        viewModel.setPosition(positionClickedIndex, null)
        view?.let { Navigation.findNavController(it).navigate(R.id.citiesListFragmentToMallListFragment) }
    }
}