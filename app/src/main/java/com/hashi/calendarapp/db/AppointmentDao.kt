package com.hashi.calendarapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hashi.calendarapp.model.Appointment
import com.hashi.calendarapp.model.Event

@Dao
interface AppointmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appointment: Appointment): Long

    @Update
    suspend fun update(appointment: Appointment)

    @Delete
    suspend fun delete(appointment: Appointment)

    @Query("SELECT * FROM appointments WHERE date = :date")
    fun getAppointmentsByDate(date: String): LiveData<List<Appointment>>

    @Query("SELECT * FROM appointments WHERE title LIKE :searchQuery")
    fun searchAppointmentsByTitle(searchQuery: String): LiveData<List<Appointment>>
}
