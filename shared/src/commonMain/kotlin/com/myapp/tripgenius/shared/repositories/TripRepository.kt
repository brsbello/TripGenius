package com.myapp.tripgenius.shared.repositories

import com.myapp.tripgenius.shared.models.Trip

class TripRepository {
    fun getTrips(): List<Trip> {
        return listOf(
            Trip(1, "Praia do Sol", "Bahia, Brasil", "Uma praia linda com águas cristalinas."),
            Trip(2, "Montanhas Geladas", "Chile", "Aventura nas montanhas com neve o ano todo."),
            Trip(3, "Cidade Antiga", "Itália", "História e cultura em cada esquina.")
        )
    }
}