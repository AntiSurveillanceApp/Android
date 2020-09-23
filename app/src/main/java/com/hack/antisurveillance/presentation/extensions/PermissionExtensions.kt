package com.hack.antisurveillance.presentation.extensions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

const val fineLocation = Manifest.permission.ACCESS_FINE_LOCATION
const val courseLocation = Manifest.permission.ACCESS_COARSE_LOCATION
const val backgroundLocation = Manifest.permission.ACCESS_BACKGROUND_LOCATION
const val recordAudio = android.Manifest.permission.RECORD_AUDIO

fun Context.isLocationPermissionsDenied(): Boolean {
    return fineLocation.isDenied(this)
            && courseLocation.isDenied(this)
            && backgroundLocation.isDenied(this)
}

fun String.isGranted(context: Context): Boolean {
    val result = ContextCompat.checkSelfPermission(context, this)
    return result == PackageManager.PERMISSION_GRANTED
}

fun String.isDenied(context: Context): Boolean {
    val result = ContextCompat.checkSelfPermission(context, this)
    return result == PackageManager.PERMISSION_DENIED
}

fun String.shouldShowRationale(activity: Activity): Boolean {
    return ActivityCompat.shouldShowRequestPermissionRationale(activity, this)
}