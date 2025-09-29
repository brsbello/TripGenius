package com.myapp.tripgenius.shared.domain.repository

import com.myapp.tripgenius.shared.domain.model.Trip

interface TripRepository {
    suspend fun getTrips(): List<Trip>
    suspend fun addTrip(trip: Trip)
    suspend fun updateTrip(trip: Trip)
    suspend fun deleteTrip(id: Long)
}
