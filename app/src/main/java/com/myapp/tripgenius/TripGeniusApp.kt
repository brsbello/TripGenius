package com.myapp.tripgenius

import android.app.Application
import com.myapp.tripgenius.di.appModule
import com.myapp.tripgenius.di.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TripGeniusApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TripGeniusApp)
            modules(appModule, databaseModule)
        }
    }
}
