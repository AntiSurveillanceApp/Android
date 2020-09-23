package com.hack.antisurveillance.domain.usecases

import com.hack.antisurveillance.data.AppPreferences
import com.hack.antisurveillance.domain.UseCase

class SaveSecureStartupUseCase(private val appPreferences: AppPreferences) : UseCase {
    operator fun invoke(enabled: Boolean) {
        appPreferences.setSecureStartup(enabled)
    }
}