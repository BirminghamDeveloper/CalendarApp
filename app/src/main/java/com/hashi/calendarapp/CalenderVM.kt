package com.hashi.calendarapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.hashi.calendarapp.db.AppDatabase
import com.hashi.calendarapp.model.Appointment
import com.hashi.calendarapp.repo.AppointmentRepository
import kotlinx.coroutines.launch

class CalendarVM(application: Application) : AndroidViewModel(application) {

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
        val appointmentsLiveData = repository.getAppointmentsByDate(date)
        _appointments.addSource(appointmentsLiveData) { appointments ->
            _appointments.value = appointments
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
        val searchResultsLiveData = repository.searchAppointmentsByTitle(searchQuery)
        _searchResults.addSource(searchResultsLiveData) { results ->
            _searchResults.value = results
        }
    }
}


/*
(application: Application) : AndroidViewModel(application) {

    private val repository: AppointmentRepository
    private val _selectedDate = MutableLiveData<String>()
    val selectedDate: LiveData<String> get() = _selectedDate

    private val _appointments = MediatorLiveData<List<Appointment>>()
    val appointments: LiveData<List<Appointment>> get() = _appointments

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
}
 */