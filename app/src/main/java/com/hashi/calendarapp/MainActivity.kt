package com.hashi.calendarapp

import java.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProvider
import com.hashi.calendarapp.model.Appointment
import androidx.lifecycle.Observer
import com.hashi.calendarapp.viewmodel.CalendarVM
import com.hashinology.calendarapp.R
import java.util.*



class MainActivity : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var selectedDateText: TextView
    private lateinit var addAppointmentButton: Button
    private lateinit var appointmentListView: ListView
    private lateinit var searchView: SearchView

    private lateinit var viewModel: CalendarVM
    private lateinit var adapter: AppointmentAdapter

    companion object {
        const val CHANNEL_ID = "appointment_channel"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()

        // Initialize the ViewModel using ViewModelProvider
        viewModel = ViewModelProvider(this).get(CalendarVM::class.java)

        calendarView = findViewById(R.id.calendarView)
        selectedDateText = findViewById(R.id.selectedDateText)
        addAppointmentButton = findViewById(R.id.addAppointmentButton)
        appointmentListView = findViewById(R.id.appointmentListView)
        searchView = findViewById(R.id.searchView)

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

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchAppointments(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.searchAppointments(it)
                }
                return true
            }
        })

        viewModel.selectedDate.observe(this, Observer { date ->
            selectedDateText.text = "Selected Date: $date"
        })

        viewModel.appointments.observe(this, Observer { appointments ->
            updateAppointmentList(appointments)
        })

        viewModel.searchResults.observe(this, Observer { appointments ->
            updateAppointmentList(appointments)
        })

        adapter = AppointmentAdapter(this, emptyList())
        appointmentListView.adapter = adapter

        appointmentListView.setOnItemClickListener { _, _, position, _ ->
            val appointment = adapter.getItem(position)
            showEditDeleteDialog(appointment!!)
        }
    }

    private fun showAddAppointmentDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_appointment, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Add Appointment")

        val alertDialog = dialogBuilder.show()

        val appointmentTitle = dialogView.findViewById<EditText>(R.id.appointmentTitle)
        val appointmentDescription = dialogView.findViewById<EditText>(R.id.appointmentDescription)
        val saveButton = dialogView.findViewById<Button>(R.id.saveButton)

        saveButton.setOnClickListener {
            val title = appointmentTitle.text.toString()
            val description = appointmentDescription.text.toString()
            val date = selectedDateText.text.toString().replace("Selected Date: ", "")

            if (title.isNotEmpty() && description.isNotEmpty()) {
                val appointment = Appointment(title = title, description = description, date = date)
                viewModel.addAppointment(appointment)
                scheduleNotification(appointment)
                alertDialog.dismiss()
            } else {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showEditDeleteDialog(appointment: Appointment) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_appointment, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Edit/Delete Appointment")

        val alertDialog = dialogBuilder.show()

        val appointmentTitle = dialogView.findViewById<EditText>(R.id.appointmentTitle)
        val appointmentDescription = dialogView.findViewById<EditText>(R.id.appointmentDescription)
        val saveButton = dialogView.findViewById<Button>(R.id.saveButton)
        val deleteButton = dialogView.findViewById<Button>(R.id.deleteButton)

        appointmentTitle.setText(appointment.title)
        appointmentDescription.setText(appointment.description)

        saveButton.setOnClickListener {
            val title = appointmentTitle.text.toString()
            val description = appointmentDescription.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                val updatedAppointment = appointment.copy(title = title, description = description)
                viewModel.updateAppointment(updatedAppointment)
                alertDialog.dismiss()
            } else {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }

        deleteButton.setOnClickListener {
            viewModel.deleteAppointment(appointment)
            alertDialog.dismiss()
        }
    }

    private fun updateAppointmentList(appointments: List<Appointment>) {
        adapter = AppointmentAdapter(this, appointments)
        appointmentListView.adapter = adapter
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Appointment Channel"
            val descriptionText = "Channel for appointment notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun scheduleNotification(appointment: Appointment) {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(appointment.title)
            .setContentText("Scheduled on ${appointment.date}")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(appointment.id, notification)
    }
}