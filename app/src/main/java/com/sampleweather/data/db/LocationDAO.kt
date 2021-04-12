package com.sampleweather.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sampleweather.data.model.Location

@Dao
interface LocationDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: Location): Long

    @Update
    suspend fun updateLocation(location: Location): Int

    @Delete
    suspend fun deleteLocation(location: Location): Int

    @Query("DELETE FROM city_data_table")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM city_data_table ORDER BY id DESC")
    fun getAllLocations(): LiveData<List<Location>>
}