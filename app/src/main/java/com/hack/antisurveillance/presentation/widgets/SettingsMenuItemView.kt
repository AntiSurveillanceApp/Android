package com.hack.antisurveillance.presentation.widgets

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import com.hack.antisurveillance.R

class SettingsMenuItemView(context: Context) : FrameLayout(context) {

    init {
        View.inflate(context, R.layout.menu_item_view, this)
    }
}