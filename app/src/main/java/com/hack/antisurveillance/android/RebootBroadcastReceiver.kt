package com.hack.antisurveillance.android

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.hack.antisurveillance.android.recorder.AudioRecorderService
import org.koin.core.KoinComponent
import org.koin.core.inject

class RebootBroadcastReceiver : BroadcastReceiver(), KoinComponent {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            if (intent != null && intent.action == "android.intent.action.BOOT_COMPLETED") {
                val workManager: CappyWorkManager by inject()
                workManager.initialize()
                startRecordMicService(context)
            }
        }
    }

    private fun startRecordMicService(context: Context) {
        context.run {
            ContextCompat.startForegroundService(
                this,
                Intent(this, AudioRecorderService::class.java)
            )
        }
    }
}