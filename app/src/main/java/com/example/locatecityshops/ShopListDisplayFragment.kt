package com.example.locatecityshops

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.locatecityshops.recycler_view.DisplayLocationListFragment
import com.example.locatecityshops.recycler_view.RecyclerRow
import com.example.locatecityshops.repository.CitiesRepository
import com.example.locatecityshops.viewmodel.CitiesViewModel
import com.example.locatecityshops.viewmodel.CitiesViewModelFactory

class ShopListDisplayFragment : DisplayLocationListFragment() {
    private var shops: List<RecyclerRow>? = null
    private var allShops: MutableList<RecyclerRow> = mutableListOf()
    private lateinit var viewModel: CitiesViewModel
    private var index = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.setSearchInstructionEntity(resources.getString(R.string.shops))
        val viewModelFactory = CitiesViewModelFactory(CitiesRepository())

        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(CitiesViewModel::class.java)
        viewModel.isConnected.observe(viewLifecycleOwner, Observer { isConnected ->
            viewModel.citiesList.observe(viewLifecycleOwner, Observer { responseCities ->
                if (isConnected || (!isConnected && responseCities != null)) {
                    val cities = responseCities.cities
                    if (!isConnected) {
                        Toast.makeText(context?.applicationContext, resources.getString(R.string.internet_connection_error), Toast.LENGTH_LONG)
                            .show()
                    }
                    viewModel.recyclerPositionSelected.observe(viewLifecycleOwner, Observer { position ->
                        if (position.cityPosition != null && position.mallPosition != null) {
                            shops =
                                cities[position.cityPosition].malls[position.mallPosition].shops.map { shop ->
                                    RecyclerRow(shop.name, null, null)
                                }
                        } else if (position.cityPosition != null) {
                            cities[position.cityPosition].malls.forEach { mall ->
                                mall.shops.forEach { shop ->
                                    allShops.add(index++, RecyclerRow(shop.name, null, null))
                                }
                            }
                            shops = allShops.toList()
                        }
                        shops?.toTypedArray()?.let { cityList -> this.submitList(cityList) }
                    })
                } else {
                    Navigation.findNavController(view).navigate(R.id.citiesListFragmentToNoDataAlertFragment)
                }
            })
        })
    }

    override fun onItemClicked(positionClickedIndex: Int) {}
}