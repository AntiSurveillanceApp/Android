package com.hack.antisurveillance.data

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.hack.antisurveillance.domain.objects.*

class AppPreferencesImpl(appContext: Application) : AppPreferences {

    private val appPreferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(appContext)

    override fun saveCoordinate(lat: Double, lng: Double, zoom: Float) {
        appPreferences.edit().putString(MAP_FAKE_LAT, lat.toString()).apply()
        appPreferences.edit().putString(MAP_FAKE_LNG, lng.toString()).apply()
        appPreferences.edit().putString(MAP_FAKE_ZOOM, zoom.toString()).apply()
    }

    override fun clearCoordinate() {
        appPreferences.edit().putString(MAP_FAKE_LAT, null).apply()
        appPreferences.edit().putString(MAP_FAKE_LNG, null).apply()
        appPreferences.edit().putString(MAP_FAKE_ZOOM, null).apply()
    }

    override fun getCoordinate(): Coordinate? {
        val lat =
            appPreferences.getString(MAP_FAKE_LAT, null).orEmpty().toDoubleOrNull() ?: return null
        val lng =
            appPreferences.getString(MAP_FAKE_LNG, null).orEmpty().toDoubleOrNull() ?: return null
        val zoom =
            appPreferences.getString(MAP_FAKE_ZOOM, null).orEmpty().toFloatOrNull() ?: return null
        return Coordinate(lat, lng, zoom)
    }

    override fun getCurrentLanguage(): Languages {
        return appPreferences.getInt(LANG, 0).lang()
    }

    override fun setCurrentLanguage(lang: Languages) {
        appPreferences.edit().putInt(LANG, lang.ordinal).apply()
    }

    override fun setAppTheme(theme: Themes) {
        appPreferences.edit().putInt(THEME, theme.ordinal).apply()
    }

    override fun getAppTheme(): Themes {
        return appPreferences.getInt(THEME, 0).getTheme()
    }

    override fun isMicBlocked(): Boolean {
        return appPreferences.getBoolean(MIC, false)
    }

    override fun setMicBlocked(isBlocked: Boolean) {
        appPreferences.edit().putBoolean(MIC, isBlocked).apply()
    }

    override fun setRootAccess(enabled: Boolean) {
        appPreferences.edit().putBoolean(ROOT_ACCESS, enabled).apply()
    }

    override fun isRootAccess(): Boolean {
        return appPreferences.getBoolean(ROOT_ACCESS, false)
    }

    override fun setSecureStartup(enabled: Boolean) {
        appPreferences.edit().putBoolean(SECURE_STARTUP, enabled).apply()
    }

    override fun isSecureStartup(): Boolean {
        return appPreferences.getBoolean(SECURE_STARTUP, false)
    }

    companion object {
        private const val MAP_FAKE_LAT = "map_fake_lat"
        private const val MAP_FAKE_LNG = "map_fake_lng"
        private const val MAP_FAKE_ZOOM = "map_fake_zoom"
        private const val LANG = "current_lang"
        private const val THEME = "app_theme"
        private const val MIC = "microphone"
        private const val ROOT_ACCESS = "root_access"
        private const val SECURE_STARTUP = "secure_startup"
    }
}