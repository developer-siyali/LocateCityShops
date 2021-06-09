package com.example.locatecityshops.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.locatecityshops.model.Cities
import com.example.locatecityshops.model.City
import com.example.locatecityshops.model.ClickIndices
import com.example.locatecityshops.repository.CitiesRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class CitiesViewModel(private val repository: CitiesRepository): ViewModel() {

    private val _citiesApiResponse: MutableLiveData<Response<Cities>> = MutableLiveData()
    private val _recyclerPositionSelected: MutableLiveData<ClickIndices> = MutableLiveData()
    private val _whichButtonClicked: MutableLiveData<Boolean> = MutableLiveData()
    private val _isConnected: MutableLiveData<Boolean> = MutableLiveData()
    private val _citiesList: MutableLiveData<Cities> = MutableLiveData()

    val citiesApiResponse: LiveData<Response<Cities>> get() = _citiesApiResponse
    val recyclerPositionSelected: LiveData<ClickIndices> get() = _recyclerPositionSelected
    val whichButtonClicked: LiveData<Boolean> get() = _whichButtonClicked
    val isConnected: LiveData<Boolean> get() = _isConnected
    val citiesList: LiveData<Cities> get() = _citiesList

    fun getCities() {
        viewModelScope.launch {
            val response: Response<Cities> = repository.getCities()
            _citiesApiResponse.value = response
        }
    }

    fun setPosition(cityPosition: Int?, mallPosition: Int?) {
        _recyclerPositionSelected.value = if (cityPosition == null) ClickIndices(recyclerPositionSelected.value?.cityPosition, mallPosition)
        else ClickIndices(cityPosition, mallPosition)
    }

    fun setConnectivityIndicator(isConnected: Boolean) {
        _isConnected.value = isConnected
    }

    fun setCites(cities: List<City>) {
        _citiesList.value = Cities(cities)
    }

    fun setButtonClickFlag(whichButtonClicked: Boolean) {
        _whichButtonClicked.value = whichButtonClicked
    }
}