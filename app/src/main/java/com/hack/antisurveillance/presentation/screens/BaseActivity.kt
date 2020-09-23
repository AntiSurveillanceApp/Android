package com.hack.antisurveillance.presentation.screens

import android.content.Context
import android.content.res.Configuration
import com.akexorcist.localizationactivity.core.LocalizationApplicationDelegate
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.hack.antisurveillance.domain.objects.getLocale
import com.hack.antisurveillance.domain.usecases.GetLanguageUseCase
import org.koin.android.ext.android.inject

abstract class BaseActivity(contentLayoutId: Int) : LocalizationActivity(contentLayoutId) {

    private val getLanguageUseCase: GetLanguageUseCase by inject()

    private val localizationDelegate = LocalizationApplicationDelegate()

    override fun attachBaseContext(base: Context) {
        localizationDelegate.setDefaultLanguage(base, getLanguageUseCase().getLocale())
        super.attachBaseContext(localizationDelegate.attachBaseContext(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localizationDelegate.onConfigurationChanged(this)
    }

    override fun getApplicationContext(): Context {
        return localizationDelegate.getApplicationContext(super.getApplicationContext())
    }
}