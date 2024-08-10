package com.hashi.calendarapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hashi.calendarapp.model.Appointment
import com.hashinology.calendarapp.R

class AppointmentAdapter(
    context: Context,
    val appointments: MutableList<Appointment>,
    val onClick: OnClick
) : RecyclerView.Adapter<AppointmentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.appointment_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return appointments.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appointment = appointments[position]
        holder.appointmentTitle.text = appointment.title
        holder.appointmentDecription.text = appointment.description

        holder.itemView.setOnClickListener {
            onClick.onAppointmentclick(appointment)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val appointmentTitle = itemView.findViewById<TextView>(R.id.appointmentTitle)
        val appointmentDecription = itemView.findViewById<TextView>(R.id.appointmentDescription)
    }

    fun updateItem(newItem: List<Appointment>) {
        appointments.clear()
        //.addAll() is only Available in the MutableList not normal List
        appointments.addAll(newItem)
        // Notify Adapter of the change
        notifyDataSetChanged()
    }
}