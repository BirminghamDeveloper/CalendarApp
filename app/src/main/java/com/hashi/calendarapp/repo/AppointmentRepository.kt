package com.hashi.calendarapp.repo

import androidx.lifecycle.LiveData
import com.hashi.calendarapp.db.AppointmentDao
import com.hashi.calendarapp.db.EventDao
import com.hashi.calendarapp.model.Appointment
import com.hashi.calendarapp.model.Event

class AppointmentRepository(private val appointmentDao: AppointmentDao) {

    fun getAppointmentsByDate(date: String): LiveData<List<Appointment>> {
        return appointmentDao.getAppointmentsByDate(date)
    }

    fun searchAppointmentsByTitle(searchQuery: String): LiveData<List<Appointment>> {
        return appointmentDao.searchAppointmentsByTitle("%$searchQuery%")
    }

    suspend fun insert(appointment: Appointment): Long {
        return appointmentDao.insert(appointment)
    }

    suspend fun update(appointment: Appointment) {
        appointmentDao.update(appointment)
    }

    suspend fun delete(appointment: Appointment) {
        appointmentDao.delete(appointment)
    }
}

class EventRepository(private val eventDao: EventDao) {

    fun getEventsByDate(date: String): LiveData<List<Event>> {
        return eventDao.getEventsByDate(date)
    }

    fun searchEventsByName(searchQuery: String): LiveData<List<Event>> {
        return eventDao.searchEventsByName("%$searchQuery%")
    }

    suspend fun insert(event: Event): Long {
        return eventDao.insert(event)
    }

    suspend fun update(event: Event) {
        eventDao.update(event)
    }

    suspend fun delete(event: Event) {
        eventDao.delete(event)
    }
}
