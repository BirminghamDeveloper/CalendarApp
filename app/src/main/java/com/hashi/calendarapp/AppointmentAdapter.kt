package com.hashi.calendarapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.hashi.calendarapp.model.Appointment
import com.hashinology.calendarapp.R

class AppointmentAdapter(context: Context, appointments: List<Appointment>) :
    ArrayAdapter<Appointment>(context, 0, appointments) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val appointment = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.appointment_list_item, parent, false)

        val titleTextView = view.findViewById<TextView>(R.id.appointmentTitle)
        val descriptionTextView = view.findViewById<TextView>(R.id.appointmentDescription)

        titleTextView.text = appointment?.title
        descriptionTextView.text = appointment?.description

        return view
    }
}