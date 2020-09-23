package com.hack.antisurveillance.android.audiorecorder

import android.content.Context
import android.os.FileObserver
import com.hack.antisurveillance.android.recorder.AmrFileObserver
import com.hack.antisurveillance.presentation.extensions.audioStorage

class AudioRecordManager(private val context: Context) {

    private var amrAudioRecorder: AMRAudioRecorder? = null
    private var fileObserver: FileObserver? = null

    fun startRecord() {
        amrAudioRecorder = AMRAudioRecorder(context.audioStorage().absolutePath).run {
            start()

            fileObserver = AmrFileObserver(audioFilePath) {
                fileObserver?.stopWatching()
                fileObserver = null
                stop()
                startRecord()
            }
            fileObserver?.startWatching()
            this
        }
    }

    fun performStop() {
        fileObserver?.stopWatching()
        fileObserver = null
        amrAudioRecorder?.stop()
    }

    companion object {
        private const val KB = 1024L
        private const val MB = KB * KB
        const val FILE_SIZE = MB
    }
}