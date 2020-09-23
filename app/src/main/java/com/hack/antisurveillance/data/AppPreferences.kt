package com.hack.antisurveillance.data

import com.hack.antisurveillance.domain.objects.Coordinate
import com.hack.antisurveillance.domain.objects.Languages
import com.hack.antisurveillance.domain.objects.Themes

interface AppPreferences {
    fun saveCoordinate(lat: Double, lng: Double, zoom: Float)
    fun clearCoordinate()
    fun getCoordinate(): Coordinate?
    fun getCurrentLanguage(): Languages
    fun setCurrentLanguage(lang: Languages)
    fun setAppTheme(theme: Themes)
    fun getAppTheme(): Themes
    fun isMicBlocked(): Boolean
    fun setMicBlocked(isBlocked: Boolean)
    fun setRootAccess(enabled: Boolean)
    fun isRootAccess(): Boolean
    fun setSecureStartup(enabled: Boolean)
    fun isSecureStartup(): Boolean
}