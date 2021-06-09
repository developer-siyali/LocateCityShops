package com.example.locatecityshops.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.locatecityshops.repository.CitiesRepository

class CitiesViewModelFactory(private val repository: CitiesRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CitiesViewModel(repository) as T
    }
}