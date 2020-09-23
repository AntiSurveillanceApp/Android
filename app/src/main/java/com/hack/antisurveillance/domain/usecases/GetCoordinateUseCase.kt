package com.hack.antisurveillance.domain.usecases

import com.hack.antisurveillance.data.AppPreferences
import com.hack.antisurveillance.domain.UseCase
import com.hack.antisurveillance.domain.objects.Coordinate

class GetCoordinateUseCase(private val appPreferences: AppPreferences) : UseCase {
    operator fun invoke(): Coordinate? {
        return appPreferences.getCoordinate()
    }
}