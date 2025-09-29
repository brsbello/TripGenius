package com.myapp.tripgenius

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.myapp.tripgenius.di.appModule
import com.myapp.tripgenius.presentation.trip.TripListScreen
import com.myapp.tripgenius.ui.theme.TripGeniusTheme
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        startKoin {
            androidContext(this@MainActivity)
            modules(appModule)
        }
        setContent {
            TripGeniusTheme {
                TripListScreen(tripViewModel = koinViewModel())
            }
        }
    }
}
