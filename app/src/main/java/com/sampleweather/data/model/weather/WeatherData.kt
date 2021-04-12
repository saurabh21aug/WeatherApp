package com.sampleweather.data.model.weather

import androidx.room.Entity

@Entity
data class WeatherData(
    val coord: Coord,
    val weather: ArrayList<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int,
)









