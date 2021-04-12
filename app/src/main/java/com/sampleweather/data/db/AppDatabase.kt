package com.sampleweather.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sampleweather.data.model.Location

@Database(entities = [Location::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun locationDAO(): LocationDAO

}

