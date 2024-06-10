package com.hashi.calendarapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.hashi.calendarapp.db.AppDatabase
import com.hashi.calendarapp.model.Appointment
import com.hashi.calendarapp.model.Event
import com.hashi.calendarapp.repo.AppointmentRepository
import com.hashi.calendarapp.repo.EventRepository
import kotlinx.coroutines.launch

class CalendarVM(application: Application) : AndroidViewModel(application) {

    private val appointmentRepository: AppointmentRepository
    private val eventRepository: EventRepository

    private val _selectedDate = MutableLiveData<String>()
    val selectedDate: LiveData<String> get() = _selectedDate

    private val _appointments = MediatorLiveData<List<Appointment>>()
    val appointments: LiveData<List<Appointment>> get() = _appointments

    private val _events = MediatorLiveData<List<Event>>()
    val events: LiveData<List<Event>> get() = _events

    private val _appointmentSearchResults = MediatorLiveData<List<Appointment>>()
    val appointmentSearchResults: LiveData<List<Appointment>> get() = _appointmentSearchResults

    private val _eventSearchResults = MediatorLiveData<List<Event>>()
    val eventSearchResults: LiveData<List<Event>> get() = _eventSearchResults

    init {
        val appointmentDao = AppDatabase.getDatabase(application).appointmentDao()
        val eventDao = AppDatabase.getDatabase(application).eventDao()
        appointmentRepository = AppointmentRepository(appointmentDao)
        eventRepository = EventRepository(eventDao)

        _appointments.addSource(_selectedDate) { date ->
            date?.let {
                getAppointmentsForDate(it)
            }
        }

        _events.addSource(_selectedDate) { date ->
            date?.let {
                getEventsForDate(it)
            }
        }
    }

    fun selectDate(date: String) {
        _selectedDate.value = date
    }

    private fun getAppointmentsForDate(date: String) {
        val appointmentsLiveData = appointmentRepository.getAppointmentsByDate(date)
        _appointments.addSource(appointmentsLiveData) { appointments ->
            _appointments.value = appointments
        }
    }

    private fun getEventsForDate(date: String) {
        val eventsLiveData = eventRepository.getEventsByDate(date)
        _events.addSource(eventsLiveData) { events ->
            _events.value = events
        }
    }

    fun addAppointment(appointment: Appointment) {
        viewModelScope.launch {
            appointmentRepository.insert(appointment)
        }
    }

    fun updateAppointment(appointment: Appointment) {
        viewModelScope.launch {
            appointmentRepository.update(appointment)
        }
    }

    fun deleteAppointment(appointment: Appointment) {
        viewModelScope.launch {
            appointmentRepository.delete(appointment)
        }
    }

    fun searchAppointments(searchQuery: String) {
        val searchResultsLiveData = appointmentRepository.searchAppointmentsByTitle(searchQuery)
        _appointmentSearchResults.addSource(searchResultsLiveData) { results ->
            _appointmentSearchResults.value = results
        }
    }

    fun addEvent(event: Event) {
        viewModelScope.launch {
            eventRepository.insert(event)
        }
    }

    fun updateEvent(event: Event) {
        viewModelScope.launch {
            eventRepository.update(event)
        }
    }

    fun deleteEvent(event: Event) {
        viewModelScope.launch {
            eventRepository.delete(event)
        }
    }

    fun searchEvents(searchQuery: String) {
        val searchResultsLiveData = eventRepository.searchEventsByName(searchQuery)
        _eventSearchResults.addSource(searchResultsLiveData) { results ->
            _eventSearchResults.value = results
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