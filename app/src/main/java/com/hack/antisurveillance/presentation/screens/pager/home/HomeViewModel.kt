package com.hack.antisurveillance.presentation.screens.pager.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hack.antisurveillance.domain.usecases.GetCoordinateUseCase
import com.hack.antisurveillance.domain.usecases.GetMicUseCase

class HomeViewModel(
    private val getMicUseCase: GetMicUseCase,
    private val getCoordinateUseCase: GetCoordinateUseCase
) : ViewModel() {

    private val _micState = MutableLiveData(getMicUseCase.invoke())
    val micState: LiveData<Boolean> = _micState

    private val _locationState = MutableLiveData(getCoordinateUseCase.invoke() != null)
    val locationState: LiveData<Boolean> = _locationState

    fun checkStates() {
        _locationState.postValue(getCoordinateUseCase.invoke() != null)
        _micState.postValue(getMicUseCase.invoke())
    }
}