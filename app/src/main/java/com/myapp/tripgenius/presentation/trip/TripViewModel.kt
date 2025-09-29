package com.myapp.tripgenius.presentation.trip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.tripgenius.shared.domain.model.Trip
import com.myapp.tripgenius.shared.domain.repository.TripRepository
import com.myapp.tripgenius.shared.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TripViewModel(private val repository: TripRepository) : ViewModel() {

    private val _trips = MutableStateFlow<UiState<List<Trip>>>(UiState.Loading)
    val trips: StateFlow<UiState<List<Trip>>> = _trips

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving

    init { loadTrips() }

    fun loadTrips() = viewModelScope.launch {
        _trips.value = UiState.Loading
        try { _trips.value = UiState.Success(repository.getTrips()) }
        catch (e: Exception) { _trips.value = UiState.Error(e.localizedMessage ?: "Erro desconhecido") }
    }

    fun addTrip(trip: Trip) = viewModelScope.launch {
        _isSaving.value = true
        try { repository.addTrip(trip); loadTrips() }
        finally { _isSaving.value = false }
    }

    fun updateTrip(trip: Trip) = viewModelScope.launch {
        _isSaving.value = true
        try { repository.updateTrip(trip); loadTrips() }
        finally { _isSaving.value = false }
    }

    fun deleteTrip(trip: Trip) = viewModelScope.launch {
        _isSaving.value = true
        try { repository.deleteTrip(trip.id); loadTrips() }
        finally { _isSaving.value = false }
    }
}
