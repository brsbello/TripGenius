package com.myapp.tripgenius.shared.domain.model

data class Trip(
    val id: Long = 0,
    val title: String,
    val destination: String,
    val description: String,
    val budget: Double
)
