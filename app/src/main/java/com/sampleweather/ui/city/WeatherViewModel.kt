package com.sampleweather.ui.city

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.sampleweather.data.network.Resource
import com.sampleweather.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) :
    ViewModel() {

    var cityName = ""

    fun getCityData(lat: String, lng: String, unit: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getCityData(lat, lng,unit)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}

