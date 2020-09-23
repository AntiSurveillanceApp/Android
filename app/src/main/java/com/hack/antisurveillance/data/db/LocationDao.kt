package com.hack.antisurveillance.data.db

import androidx.room.*

@Dao
interface LocationDao {

    @Query("SELECT * FROM locations")
    suspend fun getLocations(): List<LocationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLocation(location: LocationEntity)

    @Query("DELETE FROM locations")
    suspend fun deleteAll()
}