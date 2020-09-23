package com.hack.antisurveillance.domain

import com.hack.antisurveillance.data.AppPreferences
import com.hack.antisurveillance.domain.usecases.GetRootAccessUseCase
import com.hack.antisurveillance.domain.usecases.GetSecureStartupUseCase
import com.hack.antisurveillance.domain.usecases.SaveRootAccessUseCase
import com.hack.antisurveillance.domain.usecases.SaveSecureStartupUseCase

class MockSettingsUseCaseProvider(private val appPreferences: AppPreferences) {
    fun getRootAccessUseCase() = GetRootAccessUseCase(appPreferences)
    fun saveRootAccessUseCase() = SaveRootAccessUseCase(appPreferences)

    fun getSecureStartupUseCase() = GetSecureStartupUseCase(appPreferences)
    fun saveSecureStartupUseCase() = SaveSecureStartupUseCase(appPreferences)
}