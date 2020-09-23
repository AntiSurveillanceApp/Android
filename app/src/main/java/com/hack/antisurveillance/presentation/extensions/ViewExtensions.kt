package com.hack.antisurveillance.presentation.extensions

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat


fun View.setSafeClickListener(listener: (View) -> Unit) {
    setOnClickListener(SafeClickListener(listener))
}

class SafeClickListener(
    val wrapped: (View) -> Unit
) : View.OnClickListener {

    private var lastClickTime = 0L

    override fun onClick(view: View) {
        val now = System.currentTimeMillis()
        if (now > lastClickTime + CLICK_DEBOUNCE) {
            wrapped.invoke(view)
            lastClickTime = now
        }
    }

    companion object {
        private const val CLICK_DEBOUNCE = 1000
    }
}

fun ImageView.applyTint(@ColorRes color: Int) {
    ImageViewCompat.setImageTintList(
        this,
        ColorStateList.valueOf(ContextCompat.getColor(context, color))
    )
}

fun TextView.applyTextColor(@ColorRes color: Int) {
    setTextColor(ContextCompat.getColor(context, color))
}

fun Context.getBitmapFromVector(drawableId: Int): Bitmap {
    val drawable = ContextCompat.getDrawable(this, drawableId)
    val bitmap = Bitmap.createBitmap(
        drawable!!.intrinsicWidth,
        drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}