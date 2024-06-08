package com.hashinology.calendarapp.repo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hashinology.calendarapp.db.AppDatabase
import com.hashinology.calendarapp.db.AppointmentDao
import com.hashinology.calendarapp.model.Appointment
import kotlinx.coroutines.launch

class AppointmentRepository(application: Application) : AndroidViewModel(application) {

    private val repository: AppointmentRepository
    private val _selectedDate = MutableLiveData<String>()
    val selectedDate: LiveData<String> get() = _selectedDate

    private val _appointments = MediatorLiveData<List<Appointment>>()
    val appointments: LiveData<List<Appointment>> get() = _appointments

    private val _searchResults = MediatorLiveData<List<Appointment>>()
    val searchResults: LiveData<List<Appointment>> get() = _searchResults

    init {
        val appointmentDao = AppDatabase.getDatabase(application).appointmentDao()
        repository = AppointmentRepository(appointmentDao)

        _appointments.addSource(_selectedDate) { date ->
            date?.let {
                getAppointmentsForDate(it)
            }
        }
    }

    fun selectDate(date: String) {
        _selectedDate.value = date
    }

    private fun getAppointmentsForDate(date: String) {
        viewModelScope.launch {
            val appointmentsLiveData = repository.getAppointmentsByDate(date)
            _appointments.addSource(appointmentsLiveData) { appointments ->
                _appointments.value = appointments
            }
        }
    }

    fun addAppointment(appointment: Appointment) {
        viewModelScope.launch {
            repository.insert(appointment)
        }
    }

    fun updateAppointment(appointment: Appointment) {
        viewModelScope.launch {
            repository.update(appointment)
        }
    }

    fun deleteAppointment(appointment: Appointment) {
        viewModelScope.launch {
            repository.delete(appointment)
        }
    }

    fun searchAppointments(searchQuery: String) {
        viewModelScope.launch {
            val searchResultsLiveData = repository.searchAppointmentsByTitle(searchQuery)
            _searchResults.addSource(searchResultsLiveData) { results ->
                _searchResults.value = results
            }
        }
    }
}