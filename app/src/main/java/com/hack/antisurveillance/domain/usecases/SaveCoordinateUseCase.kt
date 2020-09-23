package com.hack.antisurveillance.domain.usecases

import com.hack.antisurveillance.data.AppPreferences
import com.hack.antisurveillance.domain.UseCase
import com.hack.antisurveillance.domain.objects.Coordinate

class SaveCoordinateUseCase(private val appPreferences: AppPreferences) : UseCase {
    operator fun invoke(coordinate: Coordinate?) {
        if (coordinate != null) {
            appPreferences.saveCoordinate(coordinate.latitude, coordinate.longitude, coordinate.zoom)
        } else {
            appPreferences.clearCoordinate()
        }
    }
}