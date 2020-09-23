package com.hack.antisurveillance.di

import com.google.gson.Gson
import com.hack.antisurveillance.android.CappyWorkManager
import com.hack.antisurveillance.data.AppPreferences
import com.hack.antisurveillance.data.AppPreferencesImpl
import com.hack.antisurveillance.data.LocationDataRepository
import com.hack.antisurveillance.data.db.AntiSurveillanceDatabase
import com.hack.antisurveillance.domain.LocationRepository
import com.hack.antisurveillance.domain.MockSettingsUseCaseProvider
import com.hack.antisurveillance.domain.usecases.*
import com.hack.antisurveillance.presentation.screens.MainActivity
import com.hack.antisurveillance.presentation.screens.pager.home.HomeFragment
import com.hack.antisurveillance.presentation.screens.pager.home.HomeViewModel
import com.hack.antisurveillance.presentation.screens.pager.map.MapFragment
import com.hack.antisurveillance.presentation.screens.pager.map.MapViewModel
import com.hack.antisurveillance.presentation.screens.pager.mic.MicViewModel
import com.hack.antisurveillance.presentation.screens.pager.mic.MicrophoneFragment
import com.hack.antisurveillance.presentation.screens.pager.settings.SettingsFragment
import com.hack.antisurveillance.presentation.screens.pager.settings.SettingsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    factory { Gson() }
    single { CappyWorkManager(get()) }
}

val presentationModule = module {
    scope<MainActivity> {
        scope(named<SettingsFragment>()) {
            viewModel { SettingsViewModel(get(), get(), get(), get(), get()) }
        }
        scope(named<MapFragment>()) {
            viewModel { MapViewModel(get(), get()) }
        }

        scope(named<MicrophoneFragment>()) {
            viewModel { MicViewModel(get(), get()) }
        }

        scope(named<HomeFragment>()) {
            viewModel { HomeViewModel(get(), get()) }
        }
    }
}

val domainModule = module {
    single { SaveLanguageUseCase(get()) }
    single { GetLanguageUseCase(get()) }

    single { SaveThemeUseCase(get()) }
    single { GetThemeUseCase(get()) }

    single { SaveCoordinateUseCase(get()) }
    single { GetCoordinateUseCase(get()) }

    single { SaveMicUseCase(get()) }
    single { GetMicUseCase(get()) }

    single { SaveRootAccessUseCase(get()) }
    single { GetRootAccessUseCase(get()) }

    single { SaveSecureStartupUseCase(get()) }
    single { GetSecureStartupUseCase(get()) }

    single { MockSettingsUseCaseProvider(get()) }
}

val dataModule = module {
    single<AppPreferences> { AppPreferencesImpl(get()) }
    single { AntiSurveillanceDatabase.buildDatabase(androidApplication()) }
    single<LocationRepository> { LocationDataRepository(get()) }
}