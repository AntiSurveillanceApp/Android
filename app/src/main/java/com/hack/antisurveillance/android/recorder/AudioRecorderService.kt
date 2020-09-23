package com.hack.antisurveillance.android.recorder

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.hack.antisurveillance.R
import com.hack.antisurveillance.android.NotificationHelper
import com.hack.antisurveillance.android.audiorecorder.AudioRecordManager
import com.hack.antisurveillance.presentation.screens.MainActivity
import kotlinx.coroutines.*

class AudioRecorderService : Service() {

    private val notificationBuilder by lazy {
        NotificationHelper(
            this, FOREGROUND_SERVICE_ID,
            getString(R.string.service_channel_name),
            NOTIFICATION_CHANNEL_ID
        )
            .apply {
                setTitle(getString(R.string.app_name))
                setText(getString(R.string.service_channel_name))
                setIntent(createPendingIntent())
                setColorized(true)
            }
    }

    private val audioRecordManager: AudioRecordManager by lazy {
        AudioRecordManager(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        audioRecordManager.startRecord()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(FOREGROUND_SERVICE_ID, notificationBuilder.notification)
        return START_STICKY
    }

    override fun onDestroy() {
        audioRecordManager.performStop()
        super.onDestroy()
    }

    private fun createPendingIntent(): PendingIntent {
        val notificationIntent = Intent(
            this,
            MainActivity::class.java
        )

        return PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    companion object {
        private const val FOREGROUND_SERVICE_ID = 10102
        private const val NOTIFICATION_CHANNEL_ID = "audio_record_service_id"
    }
}