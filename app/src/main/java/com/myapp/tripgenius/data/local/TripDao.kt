package com.myapp.tripgenius.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TripDao {
    @Query("SELECT * FROM trips")
    suspend fun getAll(): List<TripEntity>

    @Insert
    suspend fun insert(trip: TripEntity)

    @Update
    suspend fun update(trip: TripEntity)

    @Delete
    suspend fun delete(trip: TripEntity)

    @Query("SELECT * FROM trips WHERE id = :id LIMIT 1")
    suspend fun findById(id: Long): TripEntity?
}
