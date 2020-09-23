package com.hack.antisurveillance.android

import android.content.Context
import android.util.Log
import com.hack.antisurveillance.presentation.extensions.getInternalStorageMapsPath
import fr.bipi.tressence.console.SystemLogTree
import fr.bipi.tressence.file.FileLoggerTree
import timber.log.Timber
import java.io.File
import java.io.IOException

object Logger {

    private var isSetup: Boolean = false

    fun setup(context: Context) {
        if (isSetup) return
        try {
            val logsDir = File(getInternalStorageMapsPath(context), "logs")
            if (!logsDir.exists()) logsDir.mkdirs()

            val tree: Timber.Tree = FileLoggerTree.Builder()
                .withFileName("file%g.log")
                .withDir(logsDir)
                .withSizeLimit(2000000)
                .withFileLimit(10)
                .withMinPriority(Log.VERBOSE)
                .appendToFile(true)
                .build()

            Timber.plant(tree)
            Timber.plant(SystemLogTree())
            isSetup = true
        } catch (error: IOException) {
            Timber.e(error)
            isSetup = false
        }
    }

    @Suppress("unused")
    fun i(msg: String) {
        Timber.i(msg)
    }

    @Suppress("unused")
    fun e(error: Throwable) {
        Timber.e(error)
    }
}