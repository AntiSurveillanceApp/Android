package com.hack.antisurveillance.domain.usecases

import com.hack.antisurveillance.data.AppPreferences
import com.hack.antisurveillance.domain.objects.Languages
import com.hack.antisurveillance.domain.UseCase

class SaveLanguageUseCase(private val appPreferences: AppPreferences) : UseCase {
    operator fun invoke(lang: Languages) {
        appPreferences.setCurrentLanguage(lang)
    }
}