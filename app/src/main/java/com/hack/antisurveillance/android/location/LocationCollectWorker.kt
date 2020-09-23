package com.hack.antisurveillance.android.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.android.gms.location.LocationServices
import com.hack.antisurveillance.android.Logger
import com.hack.antisurveillance.data.db.LocationEntity
import com.hack.antisurveillance.domain.LocationRepository
import com.hack.antisurveillance.presentation.extensions.isLocationPermissionsDenied
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class LocationCollectWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params), KoinComponent {

    private val coroutineScope = CoroutineScope(Dispatchers.IO + Job())

    @SuppressLint("MissingPermission")
    override suspend fun doWork(): Result {

        val fusedLocation = LocationServices.getFusedLocationProviderClient(applicationContext)
        if (applicationContext.isLocationPermissionsDenied()) {
            return Result.failure()
        } else {
            fusedLocation.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        processLocation(it.latitude, it.longitude)
                    }
                }
        }

        return Result.success()
    }

    private fun processLocation(latitude: Double, longitude: Double) {
        val locationRepository: LocationRepository by inject()
        coroutineScope.launch {
            locationRepository.addLocation(LocationEntity(0, latitude, longitude))
        }
    }
}