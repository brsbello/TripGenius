package com.myapp.tripgenius.presentation.trip

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

@Composable
fun TripListScreen(
    tripViewModel: TripViewModel = koinViewModel()
) {
    val trips by tripViewModel.trips.collectAsState()

    Scaffold { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(trips) { trip ->
                TripItem(trip)
            }
        }
    }
}
