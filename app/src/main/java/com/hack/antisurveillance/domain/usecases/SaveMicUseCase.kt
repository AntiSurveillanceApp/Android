package com.hack.antisurveillance.domain.usecases

import com.hack.antisurveillance.data.AppPreferences
import com.hack.antisurveillance.domain.UseCase

class SaveMicUseCase(private val appPreferences: AppPreferences) : UseCase {

    operator fun invoke(isBlocked: Boolean) {
        appPreferences.setMicBlocked(isBlocked)
    }
}