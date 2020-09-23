package com.hack.antisurveillance.data

import com.hack.antisurveillance.data.db.AntiSurveillanceDatabase
import com.hack.antisurveillance.data.db.LocationDao
import com.hack.antisurveillance.data.db.LocationEntity
import com.hack.antisurveillance.domain.LocationRepository

class LocationDataRepository(antiSurveillanceDatabase: AntiSurveillanceDatabase) : LocationRepository {

    private val locationDao: LocationDao = antiSurveillanceDatabase.locationDao

    override suspend fun addLocation(location: LocationEntity) {
        locationDao.addLocation(location)
    }

    override suspend fun deleteAll() {
        locationDao.deleteAll()
    }

    override suspend fun getLocations(): List<LocationEntity> {
        return locationDao.getLocations()
    }
}