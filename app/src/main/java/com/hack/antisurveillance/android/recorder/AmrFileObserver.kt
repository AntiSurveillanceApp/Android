package com.hack.antisurveillance.android.recorder

import android.os.FileObserver
import com.hack.antisurveillance.android.Logger
import com.hack.antisurveillance.android.audiorecorder.AudioRecordManager
import java.io.File

class AmrFileObserver(path: String, callback: () -> Unit) : FileObserver(path) {
    private val file = File(path)
    private var block: (() -> Unit)? = callback

    override fun onEvent(p0: Int, p1: String?) {
        if (file.length() >= AudioRecordManager.FILE_SIZE) {
            stopWatching()
            block?.invoke()
            block = null
            return
        }
    }
}