package com.sampleweather.data.network

import javax.inject.Inject

class ApiServiceImp @Inject constructor(private val apiService: ApiService) {

    suspend fun getCity(lat: String, lng: String, appId: String, units: String) =
        apiService.getCityData(lat, lng, appId, units)

}