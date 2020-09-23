package com.hack.antisurveillance.android

import android.content.Context
import androidx.work.*
import com.hack.antisurveillance.android.location.LocationCollectWorker
import java.util.concurrent.TimeUnit

class CappyWorkManager(appContext: Context) {

    private val workManager = WorkManager.getInstance(appContext)
    private val locationWorkName = "GetLocation"

    init {
        start()
    }

    private fun start() {
        workManager.enqueueUniquePeriodicWork(
            locationWorkName,
            ExistingPeriodicWorkPolicy.KEEP,
            PeriodicWorkRequestBuilder<LocationCollectWorker>(
                15L, TimeUnit.MINUTES,
                14L, TimeUnit.MINUTES
            ).build()
        )
    }

    fun initialize() {}
}