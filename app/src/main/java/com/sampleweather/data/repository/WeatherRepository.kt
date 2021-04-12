package com.sampleweather.data.repository

import com.sampleweather.data.network.ApiService
import com.sampleweather.data.network.ApiServiceImp
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val apiServiceImp: ApiServiceImp) {

    suspend fun getCityData(lat: String, lng: String, unit: String) =
        apiServiceImp.getCity(lat, lng, ApiService.API_Key, unit)
}