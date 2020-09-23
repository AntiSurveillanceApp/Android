package com.hack.antisurveillance.presentation.helpers

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

fun Context.startAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.parse("package:${packageName}")
    ).apply {
        addCategory(Intent.CATEGORY_DEFAULT)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(this)
    }
}