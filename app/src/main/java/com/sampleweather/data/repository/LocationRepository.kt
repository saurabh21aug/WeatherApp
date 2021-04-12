package com.sampleweather.data.repository

import com.sampleweather.data.db.LocationDAO
import com.sampleweather.data.model.Location
import javax.inject.Inject

class LocationRepository @Inject constructor(private val dao: LocationDAO) {

    val subscribers = dao.getAllLocations()

    suspend fun insert(location: Location): Long {
        return dao.insertLocation(location)
    }

    suspend fun delete(location: Location): Int {
        return dao.deleteLocation(location)
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAll()
    }
}