package com.hashinology.calendarapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hashinology.calendarapp.model.Appointment
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appointment: Appointment): Long

    @Query("SELECT * FROM appointments WHERE date = :date")
    fun getAppointmentsByDate(date: String): LiveData<List<Appointment>>
}
