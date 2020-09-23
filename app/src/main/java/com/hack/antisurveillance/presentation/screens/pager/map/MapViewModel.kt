package com.hack.antisurveillance.presentation.screens.pager.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.hack.antisurveillance.domain.objects.Coordinate
import com.hack.antisurveillance.domain.usecases.GetCoordinateUseCase
import com.hack.antisurveillance.domain.usecases.SaveCoordinateUseCase
import com.hack.antisurveillance.presentation.screens.common.SingleLiveEvent

class MapViewModel(
    private val getCoordinateUseCase: GetCoordinateUseCase,
    private val saveCoordinateUseCase: SaveCoordinateUseCase
) : ViewModel() {

    private val _mockedLocation = MutableLiveData<Coordinate>(getCoordinateUseCase.invoke())
    val mockedLocation: LiveData<Coordinate> = _mockedLocation

    private val _locationMockedEvent = MutableLiveData<SingleLiveEvent<Unit>>()
    val locationMockedEvent: LiveData<SingleLiveEvent<Unit>> = _locationMockedEvent

    private val _mockClearedEvent = MutableLiveData<SingleLiveEvent<Unit>>()
    val mockClearedEvent: LiveData<SingleLiveEvent<Unit>> = _mockClearedEvent

    private val _currentLocation = MutableLiveData<SingleLiveEvent<Coordinate>>()
    val currentLocation: LiveData<SingleLiveEvent<Coordinate>> = _currentLocation

    fun onButtonMockLocationClicked(center: LatLng, zoom: Float) {
        if (getCoordinateUseCase.invoke() == null) {
            saveCoordinateUseCase.invoke(Coordinate(center.latitude, center.longitude, zoom))
            _locationMockedEvent.postValue(SingleLiveEvent(Unit))
        } else {
            saveCoordinateUseCase.invoke(null)
            _mockClearedEvent.postValue(SingleLiveEvent(Unit))
        }
    }

    fun onMapInitialized() {
        _mockedLocation.postValue(getCoordinateUseCase.invoke())
    }

    fun onCurrentLocationFound(currentLocation: LatLng) {
        if (getCoordinateUseCase.invoke() == null) {
            val coordinate = Coordinate(
                currentLocation.latitude,
                currentLocation.longitude,
                14f
            )
            _currentLocation.postValue(SingleLiveEvent(coordinate))
        }
    }
}