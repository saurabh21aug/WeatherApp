package com.sampleweather.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "city_data_table", indices = [Index(value = ["name"], unique = true)])
data class Location(

    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var lat: String,
    var lng: String
)