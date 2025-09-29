package com.myapp.tripgenius.di

import androidx.room.Room
import com.myapp.tripgenius.data.local.AppDatabase
import com.myapp.tripgenius.presentation.trip.TripViewModel
import com.myapp.tripgenius.repository.TripRepositoryImpl
import com.myapp.tripgenius.shared.domain.repository.TripRepository
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<TripRepository> { TripRepositoryImpl(get()) }
    viewModel { TripViewModel(get()) }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "trip_db"
        ).build()
    }
    single { get<AppDatabase>().tripDao() }
}
