package com.hack.antisurveillance.domain

import com.hack.antisurveillance.data.db.LocationEntity

interface LocationRepository {
    suspend fun addLocation(location: LocationEntity)
    suspend fun deleteAll()
    suspend fun getLocations(): List<LocationEntity>
}