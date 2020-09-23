package com.hack.antisurveillance.domain.usecases

import com.hack.antisurveillance.data.AppPreferences
import com.hack.antisurveillance.domain.UseCase

class GetMicUseCase(private val appPreferences: AppPreferences) : UseCase {
    operator fun invoke(): Boolean {
        return appPreferences.isMicBlocked()
    }
}