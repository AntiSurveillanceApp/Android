package com.hack.antisurveillance.domain.usecases

import com.hack.antisurveillance.data.AppPreferences
import com.hack.antisurveillance.domain.objects.Themes
import com.hack.antisurveillance.domain.UseCase

class SaveThemeUseCase(private val appPreferences: AppPreferences) : UseCase {
    operator fun invoke(theme: Themes){
        appPreferences.setAppTheme(theme)
    }
}