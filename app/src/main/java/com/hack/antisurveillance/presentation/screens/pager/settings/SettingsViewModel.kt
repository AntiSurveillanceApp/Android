package com.hack.antisurveillance.presentation.screens.pager.settings

import androidx.lifecycle.*
import com.hack.antisurveillance.domain.MockSettingsUseCaseProvider
import com.hack.antisurveillance.domain.objects.Languages
import com.hack.antisurveillance.domain.objects.Themes
import com.hack.antisurveillance.domain.objects.lang
import com.hack.antisurveillance.domain.usecases.GetLanguageUseCase
import com.hack.antisurveillance.domain.usecases.GetThemeUseCase
import com.hack.antisurveillance.domain.usecases.SaveLanguageUseCase
import com.hack.antisurveillance.domain.usecases.SaveThemeUseCase
import com.hack.antisurveillance.presentation.screens.common.SingleLiveEvent

class SettingsViewModel(
    getLanguageUseCase: GetLanguageUseCase,
    getAppThemeUseCase: GetThemeUseCase,
    private val mockSettingsUseCaseProvider: MockSettingsUseCaseProvider,
    private val saveLanguageUseCase: SaveLanguageUseCase,
    private val saveAppThemeUseCase: SaveThemeUseCase
) : ViewModel() {

    private val _language: MutableLiveData<Languages> = MutableLiveData(getLanguageUseCase())
    val language: LiveData<Languages> = _language

    private val _openLangDialog = MutableLiveData<SingleLiveEvent<Int>>()
    val openLangDialog: LiveData<SingleLiveEvent<Int>> = _openLangDialog

    private val _onLocaleChanged = MutableLiveData<SingleLiveEvent<Int>>()
    val onLocaleChanged: LiveData<SingleLiveEvent<Int>> = _onLocaleChanged

    private val _onThemeChanged = MutableLiveData<SingleLiveEvent<Themes>>()
    val onThemeChanged: LiveData<SingleLiveEvent<Themes>> = _onThemeChanged

    private val _onSwitchEnabled = MutableLiveData(getAppThemeUseCase() == Themes.NIGHT)
    val onSwitchEnabled: LiveData<Boolean> = _onSwitchEnabled

    private val _onSwitchSecureStartup =
        MutableLiveData(mockSettingsUseCaseProvider.getSecureStartupUseCase().invoke())
    val onSwitchSecureStartup: LiveData<Boolean> = _onSwitchSecureStartup

    private val _onSwitchRootAccess =
        MutableLiveData(mockSettingsUseCaseProvider.getRootAccessUseCase().invoke())
    val onSwitchRootAccess: LiveData<Boolean> = _onSwitchRootAccess

    private val _appVersion = MutableLiveData("Version 1.1.3")
    val appVersion: LiveData<String> = _appVersion

    private var darkThemeEnabled: Boolean = false

    fun onLanguageSelected(selectedPosition: Int) {
        if (selectedPosition >= 0) {
            val lang = selectedPosition.lang()
            _language.postValue(lang)
        }
    }

    fun onSaveLanguageClicked() {
        val lang = language.value ?: return
        saveLanguageUseCase(lang)
        _onLocaleChanged.postValue(SingleLiveEvent(lang.ordinal))
    }

    fun onOpenLangDialogClicked() {
        val langOrder = language.value?.ordinal ?: 0
        _openLangDialog.postValue(SingleLiveEvent(langOrder))
    }

    fun onSwitchThemeClicked() {
        darkThemeEnabled = !darkThemeEnabled
        val theme = if (darkThemeEnabled) {
            Themes.NIGHT
        } else {
            Themes.DAY
        }
        saveAppThemeUseCase.invoke(theme)
        _onSwitchEnabled.postValue(darkThemeEnabled)
        _onThemeChanged.postValue(SingleLiveEvent(theme))
    }

    fun onSwitchSecureStartupChanged(isChecked: Boolean) {
        mockSettingsUseCaseProvider.saveSecureStartupUseCase().invoke(isChecked)
        _onSwitchSecureStartup.postValue(isChecked)
    }

    fun onSwitchRootAccessChanged(isChecked: Boolean) {
        mockSettingsUseCaseProvider.saveRootAccessUseCase().invoke(isChecked)
    }
}