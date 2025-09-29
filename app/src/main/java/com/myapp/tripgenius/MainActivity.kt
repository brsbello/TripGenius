package com.myapp.tripgenius

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.myapp.tripgenius.navigation.TripNavGraph
import com.myapp.tripgenius.ui.theme.TripGeniusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TripGeniusTheme {
                TripNavGraph()
            }
        }
    }
}
