package com.hashi.calendarapp.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.hashi.calendarapp.model.Appointment
import com.hashi.calendarapp.viewmodel.CalendarVM
import com.hashinology.calendarapp.R
import java.util.Calendar

class AppointmentsFragment : Fragment() {
    private lateinit var appointmentListView: ListView
    private lateinit var selectedDateText: TextView
    private lateinit var selectedTimeText: TextView
    private lateinit var addAppointmentButton: Button
    private lateinit var updateAppointmentButton: Button
    private lateinit var deleteAppointmentButton: Button
    private lateinit var searchAppointmentButton: Button
    private lateinit var appointmentTitle: EditText
    private lateinit var appointmentDescription: EditText
    private lateinit var searchQuery: EditText
    private lateinit var pickDateButton: Button
    private lateinit var pickTimeButton: Button

    private lateinit var viewModel: CalendarVM
    private lateinit var adapter: ArrayAdapter<Appointment>
    private var selectedAppointment: Appointment? = null
    private var selectedTime: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_appointments, container, false)
        appointmentListView = view.findViewById(R.id.appointmentListView)
        selectedDateText = view.findViewById(R.id.selectedDateText)
        selectedTimeText = view.findViewById(R.id.selectedTimeText)
        addAppointmentButton = view.findViewById(R.id.addAppointmentButton)
        updateAppointmentButton = view.findViewById(R.id.updateAppointmentButton)
        deleteAppointmentButton = view.findViewById(R.id.deleteAppointmentButton)
        searchAppointmentButton = view.findViewById(R.id.searchAppointmentButton)
        appointmentTitle = view.findViewById(R.id.appointmentTitle)
        appointmentDescription = view.findViewById(R.id.appointmentDescription)
        searchQuery = view.findViewById(R.id.searchQuery)
        pickDateButton = view.findViewById(R.id.btnPickDate)
        pickTimeButton = view.findViewById(R.id.btnPickTime)

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

        appointmentListView.setOnItemClickListener { _, _, position, _ ->
            selectedAppointment = adapter.getItem(position)
            selectedAppointment?.let {
                appointmentTitle.setText(it.title)
                appointmentDescription.setText(it.description)
                selectedTime = it.time
                selectedTimeText.text = "Selected Time: $selectedTime"

                val bundle = Bundle()
                bundle.putSerializable("appointment", it)
                findNavController().navigate(R.id.action_appointmentsFragment_to_appointmentDetailFragment, bundle)
            }
        }

        pickDateButton.setOnClickListener {
            showDatePickerDialog()
        }

        pickTimeButton.setOnClickListener {
            showTimePickerDialog()
        }

        addAppointmentButton.setOnClickListener {
            val title = appointmentTitle.text.toString()
            val description = appointmentDescription.text.toString()
            val date = viewModel.selectedDate.value ?: ""
            if (title.isNotEmpty() && description.isNotEmpty() && date.isNotEmpty() && selectedTime.isNotEmpty()) {
                val appointment = Appointment(
                    title = title,
                    description = description,
                    date = date,
                    time = selectedTime
                )
                viewModel.addAppointment(appointment)
                Toast.makeText(context, "Appointment added", Toast.LENGTH_SHORT).show()
                clearFields()
            } else {
                Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }

        updateAppointmentButton.setOnClickListener {
            selectedAppointment?.let {
                val title = appointmentTitle.text.toString()
                val description = appointmentDescription.text.toString()
                val date = viewModel.selectedDate.value ?: ""
                if (title.isNotEmpty() && description.isNotEmpty() && date.isNotEmpty() && selectedTime.isNotEmpty()) {
                    val updatedAppointment = it.copy(title = title, description = description, date = date, time = selectedTime)
                    viewModel.updateAppointment(updatedAppointment)
                    Toast.makeText(context, "Appointment updated", Toast.LENGTH_SHORT).show()
                    clearFields()
                } else {
                    Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                }
            }
        }

        deleteAppointmentButton.setOnClickListener {
            selectedAppointment?.let {
                viewModel.deleteAppointment(it)
                Toast.makeText(context, "Appointment deleted", Toast.LENGTH_SHORT).show()
                clearFields()
            }
        }

        searchAppointmentButton.setOnClickListener {
            val query = searchQuery.text.toString()
            if (query.isNotEmpty()) {
                viewModel.searchAppointments(query)
            } else {
                Toast.makeText(context, "Please enter a search query", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.appointmentSearchResults.observe(viewLifecycleOwner, Observer { searchResults ->
            adapter.clear()
            adapter.addAll(searchResults)
            adapter.notifyDataSetChanged()
        })
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
            viewModel.selectDate(selectedDate)
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
            selectedTime = "$selectedHour:$selectedMinute"
            selectedTimeText.text = "Selected Time: $selectedTime"
        }, hour, minute, true)

        timePickerDialog.show()
    }

    private fun clearFields() {
        appointmentTitle.text.clear()
        appointmentDescription.text.clear()
        searchQuery.text.clear()
        selectedAppointment = null
        selectedTime = ""
        selectedTimeText.text = "Selected Time: "
    }
}



/*lateinit var appointmentListView: ListView
lateinit var selectedDateText: TextView
lateinit var addAppointmentButton: Button
lateinit var updateAppointmentButton: Button
lateinit var deleteAppointmentButton: Button
lateinit var searchAppointmentButton: Button
lateinit var appointmentTitle: EditText
lateinit var appointmentDescription: EditText
lateinit var searchQuery: EditText

private lateinit var viewModel: CalendarVM
private lateinit var adapter: ArrayAdapter<Appointment>
private var selectedAppointment: Appointment? = null

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
    updateAppointmentButton = view.findViewById(R.id.updateAppointmentButton)
    deleteAppointmentButton = view.findViewById(R.id.deleteAppointmentButton)
    searchAppointmentButton = view.findViewById(R.id.searchAppointmentButton)
    searchQuery = view.findViewById(R.id.searchQuery)

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
            val appointment = Appointment(
                title = title,
                description = description,
                date = viewModel.selectedDate.value ?: ""
            )
            viewModel.addAppointment(appointment)
            Toast.makeText(context, "Appointment added", Toast.LENGTH_SHORT).show()
            appointmentTitle.text.clear()
            appointmentDescription.text.clear()
        } else {
            Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }
    updateAppointmentButton.setOnClickListener {
        selectedAppointment?.let {
            val title = appointmentTitle.text.toString()
            val description = appointmentDescription.text.toString()
            if (title.isNotEmpty() && description.isNotEmpty()) {
                val updatedAppointment = it.copy(title = title, description = description)
                viewModel.updateAppointment(updatedAppointment)
                Toast.makeText(context, "Appointment updated", Toast.LENGTH_SHORT).show()
                clearFields()
            } else {
                Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
    deleteAppointmentButton.setOnClickListener {
        selectedAppointment?.let {
            viewModel.deleteAppointment(it)
            Toast.makeText(context, "Appointment deleted", Toast.LENGTH_SHORT).show()
            clearFields()
        }
    }
    searchAppointmentButton.setOnClickListener {
        val query = searchQuery.text.toString()
        if (query.isNotEmpty()) {
            viewModel.searchAppointments(query).observe(viewLifecycleOwner, Observer { searchResults ->
                adapter.clear()
                adapter.addAll(searchResults)
                adapter.notifyDataSetChanged()
            })
        } else {
            Toast.makeText(context, "Please enter a search query", Toast.LENGTH_SHORT).show()
        }
    }
}

private fun showDatePickerDialog() {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
        val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
        viewModel.selectDate(selectedDate)
    }, year, month, day)

    datePickerDialog.show()
}
private fun clearFields() {
    appointmentTitle.text.clear()
    appointmentDescription.text.clear()
    searchQuery.text.clear()
    selectedAppointment = null
}*/
