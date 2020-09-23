package com.hack.antisurveillance.presentation.extensions

import android.content.Context
import java.io.File

const val APPLICATION_FOLDER_NAME = "AntiSurveillance"

fun Context.audioStorage(): File {
    val file = File(externalCacheDir, APPLICATION_FOLDER_NAME)
    if (!file.exists()) {
        file.mkdirs()
    }
    return file
}

fun getInternalStorageMapsPath(context: Context): String {
    val file = File(context.externalCacheDir, APPLICATION_FOLDER_NAME)
    if (!file.exists()) {
        file.mkdirs()
    }
    return file.absolutePath
}