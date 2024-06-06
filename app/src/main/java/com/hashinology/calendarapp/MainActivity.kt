package com.hashinology.calendarapp

import java.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.activity.enableEdgeToEdge
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.hashinology.calendarapp.model.Appointment
import androidx.lifecycle.Observer
import com.hashinology.calendarapp.R
import java.util.*



class MainActivity : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var selectedDateText: TextView
    private lateinit var addAppointmentButton: Button
    private lateinit var appointmentListView: ListView

    private lateinit var viewModel: CalenderVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the ViewModel using ViewModelProvider
        viewModel = ViewModelProvider(this).get(CalenderVM::class.java)

        calendarView = findViewById(R.id.calendarView)
        selectedDateText = findViewById(R.id.selectedDateText)
        addAppointmentButton = findViewById(R.id.addAppointmentButton)
        appointmentListView = findViewById(R.id.appointmentListView)

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val currentDate = sdf.format(Date())
        viewModel.selectDate(currentDate)
        selectedDateText.text = "Selected Date: $currentDate"

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            viewModel.selectDate(selectedDate)
        }

        addAppointmentButton.setOnClickListener {
            showAddAppointmentDialog()
        }

        // Observe selectedDate LiveData
        viewModel.selectedDate.observe(this, Observer { date ->
            selectedDateText.text = "Selected Date: $date"
            updateAppointmentList()
        })

        // Observe appointments LiveData
        viewModel.appointments.observe(this, Observer {
            updateAppointmentList()
        })

        updateAppointmentList()
    }

    private fun showAddAppointmentDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_appointment, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Add Appointment")

        val alertDialog = dialogBuilder.show()

        val appointmentTitle = dialogView.findViewById<EditText>(R.id.appointmentTitle)
        val appointmentDescription = dialogView.findViewById<EditText>(R.id.appointmentDescription)
        val saveAppointmentButton = dialogView.findViewById<Button>(R.id.saveAppointmentButton)

        saveAppointmentButton.setOnClickListener {
            val title = appointmentTitle.text.toString()
            val description = appointmentDescription.text.toString()
            val appointment = Appointment(title, description, viewModel.selectedDate.value ?: "")
            viewModel.addAppointment(appointment)
            alertDialog.dismiss()
        }
    }

    private fun updateAppointmentList() {
        val appointments = viewModel.getAppointmentsForSelectedDate()
        val adapter = AppointmentAdapter(this, appointments)
        appointmentListView.adapter = adapter
    }
}