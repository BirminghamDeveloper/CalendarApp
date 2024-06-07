package com.hashinology.calendarapp.repo

import androidx.lifecycle.LiveData
import com.hashinology.calendarapp.db.AppointmentDao
import com.hashinology.calendarapp.model.Appointment

class AppointmentRepository(private val appointmentDao: AppointmentDao) {

    fun getAppointmentsByDate(date: String): LiveData<List<Appointment>> {
        return appointmentDao.getAppointmentsByDate(date)
    }

    suspend fun insert(appointment: Appointment): Long {
        return appointmentDao.insert(appointment)
    }
}