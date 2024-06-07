package com.hashinology.calendarapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hashinology.calendarapp.db.AppDatabase
import com.hashinology.calendarapp.model.Appointment
import com.hashinology.calendarapp.repo.AppointmentRepository
import kotlinx.coroutines.launch

class CalendarVM(application: Application) : AndroidViewModel(application) {

    private val repository: AppointmentRepository
    private val _selectedDate = MutableLiveData<String>()
    val selectedDate: LiveData<String> get() = _selectedDate

    init {
        val appointmentDao = AppDatabase.getDatabase(application).appointmentDao()
        repository = AppointmentRepository(appointmentDao)
    }

    fun selectDate(date: String) {
        _selectedDate.value = date
    }

    fun getAppointmentsForSelectedDate(): LiveData<List<Appointment>> {
        return Transformations.switchMap(_selectedDate) { date ->
            repository.getAppointmentsByDate(date)
        }
    }

    fun addAppointment(appointment: Appointment) {
        viewModelScope.launch {
            repository.insert(appointment)
        }
    }
}