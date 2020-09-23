package com.hack.antisurveillance.domain.usecases

import com.hack.antisurveillance.data.AppPreferences
import com.hack.antisurveillance.domain.objects.Languages
import com.hack.antisurveillance.domain.UseCase

class GetLanguageUseCase(private val appPreferences: AppPreferences) : UseCase {
    operator fun invoke(): Languages {
        return appPreferences.getCurrentLanguage()
    }
}