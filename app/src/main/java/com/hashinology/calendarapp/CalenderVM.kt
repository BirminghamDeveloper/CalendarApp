package com.hashinology.calendarapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hashinology.calendarapp.model.Appointment

class CalenderVM(): ViewModel() {
    private val _appointments = MutableLiveData<MutableList<Appointment>>(mutableListOf())
    val appointments: LiveData<MutableList<Appointment>> get() = _appointments

    private val _selectedDate = MutableLiveData<String>()
    val selectedDate: LiveData<String> get() = _selectedDate

    fun selectDate(date: String) {
        _selectedDate.value = date
    }

    fun addAppointment(appointment: Appointment) {
        _appointments.value?.add(appointment)
        _appointments.value = _appointments.value
    }

    fun getAppointmentsForSelectedDate(): List<Appointment> {
        return _appointments.value?.filter { it.date == _selectedDate.value } ?: emptyList()
    }
}