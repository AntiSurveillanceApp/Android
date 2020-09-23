package com.hack.antisurveillance

import androidx.appcompat.app.AppCompatDelegate
import com.akexorcist.localizationactivity.ui.LocalizationApplication
import com.hack.antisurveillance.android.CappyWorkManager
import com.hack.antisurveillance.android.Logger
import com.hack.antisurveillance.data.AppPreferences
import com.hack.antisurveillance.di.appModule
import com.hack.antisurveillance.di.dataModule
import com.hack.antisurveillance.di.domainModule
import com.hack.antisurveillance.di.presentationModule
import com.hack.antisurveillance.domain.objects.Themes
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import java.util.*

class AntiSurveillanceApplication : LocalizationApplication() {

    private val appPreferences: AppPreferences by inject()
    private val cappyWorkManager: CappyWorkManager by inject()

    override fun onCreate() {
        super.onCreate()
        initializeDI()
    }

    override fun getDefaultLanguage(): Locale {
        return Locale("ru", "RU")
    }

    private fun initializeDI() {
        Logger.setup(this)

        startKoin {
            androidContext(this@AntiSurveillanceApplication)
            androidLogger(Level.ERROR)
            modules(appModule, presentationModule, domainModule, dataModule)
        }

        val theme = appPreferences.getAppTheme()
        applyAppTheme(theme)

        cappyWorkManager.initialize()
    }

    private fun applyAppTheme(theme: Themes) {
        when (theme) {
            Themes.NIGHT -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            Themes.DAY -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}