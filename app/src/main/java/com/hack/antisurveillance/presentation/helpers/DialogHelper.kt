package com.hack.antisurveillance.presentation.helpers

import android.content.Context
import android.content.DialogInterface
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hack.antisurveillance.R

fun Context.getPermissionsDeclinedCantAskMoreDialog(@StringRes message: Int, callback:DialogInterface.OnClickListener): AlertDialog {
    return MaterialAlertDialogBuilder(this)
        .setTitle(R.string.permissions_denied)
        .setMessage(message)
        .setCancelable(false)
        .setPositiveButton(R.string.ok, callback)
        .setNegativeButton(R.string.cancel, callback)
        .create()
}