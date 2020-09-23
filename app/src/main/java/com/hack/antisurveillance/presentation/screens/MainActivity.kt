package com.hack.antisurveillance.presentation.screens

import com.hack.antisurveillance.R
import java.util.*

class MainActivity : BaseActivity(R.layout.activity_main), OnLocaleChangeListener {

    override fun onLocaleChanged(locale: Locale) {
        setLanguage(locale)
    }
}