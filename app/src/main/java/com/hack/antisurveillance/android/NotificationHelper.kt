package com.hack.antisurveillance.android

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.hack.antisurveillance.R

class NotificationHelper(
    context: Context,
    private val id: Int,
    channelName: String,
    channelId: String
) {

    private val manager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val builder = NotificationCompat.Builder(context, channelId)

    val notification: Notification
        get() = builder.build()

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(channelId, channelName, importance)
            manager.createNotificationChannel(channel)
        }
        builder.apply {
            setDefaults(Notification.FLAG_FOREGROUND_SERVICE)
            setSmallIcon(R.drawable.ic_stat_vpn_lock)
            setLocalOnly(true)
            color = ContextCompat.getColor(context, R.color.colorPrimary)
        }
    }

    fun setText(text: String) {
        builder.setContentText(text)
    }

    fun setTitle(title: String) {
        builder.setContentTitle(title)
    }

    fun setProgress(max: Int = 0, progress: Int = 0, intermediate: Boolean = true) {
        builder.setProgress(max, progress, intermediate)
    }

    fun setIntent(pendingIntent: PendingIntent) {
        builder.setContentIntent(pendingIntent)
    }

    fun setOngoing(ongoing: Boolean) {
        builder.setOngoing(ongoing)
    }

    fun setColorized(isColorized: Boolean) {
        builder.setColorized(isColorized)
    }

    fun show() {
        manager.notify(id, notification)
    }
}