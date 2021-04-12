package com.sampleweather.ui.home

import android.location.Address
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sampleweather.data.model.Location

import com.sampleweather.data.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: LocationRepository) : ViewModel() {


    lateinit var addresses: List<Address>

    val subscribers = repository.subscribers


    fun insert(location: Location) = viewModelScope.launch {
        repository.insert(location)
    }


    fun delete(location: Location) = viewModelScope.launch {
        repository.delete(location)
    }

    fun clearAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }


}