package com.sampleweather.data.network

import com.sampleweather.data.model.weather.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val API_Key = "fae7190d7e6433ec3a45285ffcf55c86"
    }

    @GET("weather/")
    suspend fun getCityData(
        @Query("lat") lat: String,
        @Query("lon") lng: String,
        @Query("appid") appId: String,
        @Query("units") units: String
    ): WeatherData
}