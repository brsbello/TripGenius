package com.myapp.tripgenius.di

import com.myapp.tripgenius.presentation.trip.TripViewModel
import com.myapp.tripgenius.shared.repositories.TripRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { TripRepository() }

    viewModel { TripViewModel(get()) }
}
