package com.myapp.tripgenius.presentation.trip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.tripgenius.shared.models.Trip
import com.myapp.tripgenius.shared.repositories.TripRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TripViewModel(private val repository: TripRepository) : ViewModel() {

    private val _trips = MutableStateFlow<List<Trip>>(emptyList())
    val trips: StateFlow<List<Trip>> = _trips

    init {
        loadTrips()
    }

    private fun loadTrips() {
        viewModelScope.launch {
            _trips.value = repository.getTrips()
        }
    }
}
