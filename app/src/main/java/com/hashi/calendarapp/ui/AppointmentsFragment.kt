package com.hashi.calendarapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hashi.calendarapp.model.Appointment
import com.hashi.calendarapp.viewmodel.CalendarVM
import com.hashinology.calendarapp.R

class AppointmentsFragment : Fragment() {

    lateinit var appointmentListView: ListView
    lateinit var selectedDateText: TextView
    lateinit var addAppointmentButton: Button
    lateinit var appointmentTitle: EditText
    lateinit var appointmentDescription: EditText

    private lateinit var viewModel: CalendarVM
    private lateinit var adapter: ArrayAdapter<Appointment>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_appointments, container, false)
        appointmentListView = view.findViewById(R.id.appointmentListView)
        selectedDateText = view.findViewById(R.id.selectedDateText)
        addAppointmentButton = view.findViewById(R.id.addAppointmentButton)
        appointmentTitle = view.findViewById(R.id.appointmentTitle)
        appointmentDescription = view.findViewById(R.id.appointmentDescription)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(CalendarVM::class.java)

        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, ArrayList())

        appointmentListView.adapter = adapter

        viewModel.appointments.observe(viewLifecycleOwner, Observer { appointments ->
            adapter.clear()
            adapter.addAll(appointments)
            adapter.notifyDataSetChanged()
        })

        viewModel.selectedDate.observe(viewLifecycleOwner, Observer { date ->
            selectedDateText.text = "Selected Date: $date"
        })

        addAppointmentButton.setOnClickListener {
            val title = appointmentTitle.text.toString()
            val description = appointmentDescription.text.toString()
            if (title.isNotEmpty() && description.isNotEmpty()) {
                val appointment = Appointment(title = title, description = description, date = viewModel.selectedDate.value ?: "")
                viewModel.addAppointment(appointment)
                Toast.makeText(context, "Appointment added", Toast.LENGTH_SHORT).show()
                appointmentTitle.text.clear()
                appointmentDescription.text.clear()
            } else {
                Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}