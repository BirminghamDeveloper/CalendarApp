package com.hashinology.calendarapp

import java.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.*

data class Appointment(val title: String, val description: String, val date: String)
class MainActivity : AppCompatActivity() {
    private lateinit var calendarView: CalendarView
    private lateinit var selectedDateText: TextView
    private lateinit var addAppointmentButton: Button
    private lateinit var appointmentListView: ListView

    private val appointments = mutableListOf<Appointment>()
    private var selectedDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendarView = findViewById(R.id.calendarView)
        selectedDateText = findViewById(R.id.selectedDateText)
        addAppointmentButton = findViewById(R.id.addAppointmentButton)
        appointmentListView = findViewById(R.id.appointmentListView)

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        selectedDate = sdf.format(Date())
        selectedDateText.text = "Selected Date: $selectedDate"

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
            selectedDateText.text = "Selected Date: $selectedDate"
            updateAppointmentList()
        }

        addAppointmentButton.setOnClickListener {
            showAddAppointmentDialog()
        }

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
            val appointment = Appointment(title, description, selectedDate)
            appointments.add(appointment)
            updateAppointmentList()
            alertDialog.dismiss()
        }
    }

    private fun updateAppointmentList() {
        val filteredAppointments = appointments.filter { it.date == selectedDate }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, filteredAppointments) { view, appointment ->
            view.findViewById<TextView>(android.R.id.text1).text = appointment.title
            view.findViewById<TextView>(android.R.id.text2).text = appointment.description
        }
        appointmentListView.adapter = adapter
    }
}