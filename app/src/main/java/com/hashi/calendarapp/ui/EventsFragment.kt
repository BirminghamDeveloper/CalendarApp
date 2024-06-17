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
import androidx.navigation.fragment.findNavController
import com.hashi.calendarapp.model.Event
import com.hashi.calendarapp.viewmodel.CalendarVM
import com.hashinology.calendarapp.R

class EventsFragment : Fragment() {
    lateinit var selectedDateText: TextView
    lateinit var eventListView: ListView
    lateinit var addEventButton: Button
    lateinit var eventName: EditText
    lateinit var eventDescription: EditText

    private lateinit var viewModel: CalendarVM
    private lateinit var adapter: ArrayAdapter<Event>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_events, container, false)
        selectedDateText = view.findViewById(R.id.selectedDateText)
        eventListView = view.findViewById(R.id.eventListView)
        addEventButton = view.findViewById(R.id.addEventButton)
        eventName = view.findViewById(R.id.eventName)
        eventDescription = view.findViewById(R.id.eventDescription)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(CalendarVM::class.java)

        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, ArrayList())
        eventListView.adapter = adapter

        viewModel.events.observe(viewLifecycleOwner, Observer { events ->
            adapter.clear()
            adapter.addAll(events)
            adapter.notifyDataSetChanged()
        })

        viewModel.selectedDate.observe(viewLifecycleOwner, Observer { date ->
            selectedDateText.text = "Selected Date: $date"
        })

        addEventButton.setOnClickListener {
            val name = eventName.text.toString()
            val description = eventDescription.text.toString()
            if (name.isNotEmpty() && description.isNotEmpty()) {
                val event = Event(name = name, description = description, date = viewModel.selectedDate.value ?: "")
                viewModel.addEvent(event)
                Toast.makeText(context, "Event added", Toast.LENGTH_SHORT).show()
                eventName.text.clear()
                eventDescription.text.clear()
            } else {
                Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}