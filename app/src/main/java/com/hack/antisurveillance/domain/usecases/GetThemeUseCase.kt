package com.hack.antisurveillance.domain.usecases

import com.hack.antisurveillance.data.AppPreferences
import com.hack.antisurveillance.domain.objects.Themes
import com.hack.antisurveillance.domain.UseCase

class GetThemeUseCase(private val appPreferences: AppPreferences) : UseCase {
    operator fun invoke(): Themes {
        return appPreferences.getAppTheme()
    }
}