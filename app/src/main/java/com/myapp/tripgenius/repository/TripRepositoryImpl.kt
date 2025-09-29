package com.myapp.tripgenius.repository

import com.myapp.tripgenius.data.local.TripDao
import com.myapp.tripgenius.data.local.TripEntity
import com.myapp.tripgenius.shared.domain.model.Trip
import com.myapp.tripgenius.shared.domain.repository.TripRepository

class TripRepositoryImpl(
    private val dao: TripDao
) : TripRepository {

    override suspend fun getTrips(): List<Trip> =
        dao.getAll().map { it.toDomain() }

    override suspend fun addTrip(trip: Trip) {
        dao.insert(trip.toEntity())
    }

    override suspend fun updateTrip(trip: Trip) {
        dao.update(trip.toEntity())
    }

    override suspend fun deleteTrip(id: Long) {
        dao.findById(id)?.let { dao.delete(it) }
    }

    private fun TripEntity.toDomain() = Trip(id, title, destination, description, budget)
    private fun Trip.toEntity() = TripEntity(id, title, destination, description, budget)
}
