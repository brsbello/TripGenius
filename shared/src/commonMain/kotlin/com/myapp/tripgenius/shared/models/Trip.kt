package com.myapp.tripgenius.shared.models

import kotlinx.serialization.Serializable

@Serializable
data class Trip(
    val id: Int,
    val name: String,
    val location: String,
    val description: String
)