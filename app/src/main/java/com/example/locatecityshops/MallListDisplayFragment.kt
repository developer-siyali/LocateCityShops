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

class MallListDisplayFragment : DisplayLocationListFragment() {
    private var malls: List<RecyclerRow>? = null
    private lateinit var viewModel: CitiesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.setViewAllShopsButton(true)
        val viewModelFactory = CitiesViewModelFactory(CitiesRepository())
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(CitiesViewModel::class.java)
        viewModel.isConnected.observe(viewLifecycleOwner, Observer { isConnected ->
            viewModel.citiesList.observe(viewLifecycleOwner, Observer { responseCities ->
                if (isConnected || (!isConnected && responseCities != null)) {
                    viewModel.recyclerPositionSelected.observe(viewLifecycleOwner, Observer { position ->
                        if (!isConnected) {
                            Toast.makeText(context?.applicationContext, "The was internet connection found, previously save information is displayed", Toast.LENGTH_LONG)
                                .show()
                        }
                        if (position.cityPosition != null) this.setSearchInstructionEntity(resources.getString(R.string.malls), responseCities.cities[position?.cityPosition].name)
                        malls = position.cityPosition?.let { cityPosition ->
                            responseCities.cities[cityPosition].malls.map { mall ->
                                RecyclerRow(mall.name, mall.shops.size, resources.getString(R.string.shop))
                            }
                        }
                        malls?.toTypedArray()?.let { mallList -> this.submitList(mallList) }
                    })
                } else {
                    Navigation.findNavController(view).navigate(R.id.citiesListFragmentToNoDataAlertFragment)
                }
            })
        })

        viewModel.whichButtonClicked.observe(viewLifecycleOwner, Observer { isButtonClicked ->
            if (isButtonClicked) {
                viewModel.setButtonClickFlag(false)
                viewModel.setPosition(null, null)
                Navigation.findNavController(view).navigate(R.id.mallListFragmentToViewShopFragment)
            }
        })
    }

    override fun onItemClicked(positionClickedIndex: Int) {
        this.setViewAllShopsButton(false)
        viewModel.setPosition(null, positionClickedIndex)
        view?.let { Navigation.findNavController(it).navigate(R.id.mallListFragmentToViewShopFragment) }
    }
}