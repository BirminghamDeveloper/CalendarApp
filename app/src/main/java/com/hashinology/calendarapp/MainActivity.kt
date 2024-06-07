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

    private lateinit var viewModel: CalendarVM
    private lateinit var adapter: AppointmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the ViewModel using ViewModelProvider
        viewModel = ViewModelProvider(this).get(CalendarVM::class.java)

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

        viewModel.selectedDate.observe(this, Observer { date ->
            selectedDateText.text = "Selected Date: $date"
            viewModel.getAppointmentsForSelectedDate().observe(this, Observer { appointments ->
                updateAppointmentList(appointments)
            })
        })

        adapter = AppointmentAdapter(this, emptyList())
        appointmentListView.adapter = adapter
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

    private fun updateAppointmentList(appointments: List<Appointment>) {
        adapter.clear()
        adapter.addAll(appointments)
        adapter.notifyDataSetChanged()
    }
}