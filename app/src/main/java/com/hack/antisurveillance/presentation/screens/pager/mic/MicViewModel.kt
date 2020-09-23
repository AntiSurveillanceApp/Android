package com.hack.antisurveillance.presentation.screens.pager.mic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hack.antisurveillance.domain.usecases.GetMicUseCase
import com.hack.antisurveillance.domain.usecases.SaveMicUseCase

class MicViewModel(
    getMicUseCase: GetMicUseCase,
    private val saveMicUseCase: SaveMicUseCase
) : ViewModel() {

    private val _isMicBlocked = MutableLiveData(getMicUseCase.invoke())
    val isMicBlocked: LiveData<Boolean> = _isMicBlocked

    fun onMicrophoneToggled(checked: Boolean) {
        saveMicUseCase.invoke(checked)
    }
}