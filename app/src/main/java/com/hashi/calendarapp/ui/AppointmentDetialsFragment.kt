package com.hashi.calendarapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hashi.calendarapp.model.Appointment
import com.hashinology.calendarapp.R

class AppointmentDetailFragment : Fragment() {

    private lateinit var appointmentTitleDetail: TextView
    private lateinit var appointmentDescriptionDetail: TextView
    private lateinit var appointmentDateDetail: TextView
    private lateinit var appointmentTimeDetail: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_appointment_detials, container, false)
        appointmentTitleDetail = view.findViewById(R.id.appointmentTitleDetail)
        appointmentDescriptionDetail = view.findViewById(R.id.appointmentDescriptionDetail)
        appointmentDateDetail = view.findViewById(R.id.appointmentDateDetail)
        appointmentTimeDetail = view.findViewById(R.id.appointmentTimeDetail)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val appointment = it.getSerializable("appointment") as Appointment
            appointment?.let { apt ->
                appointmentTitleDetail.text = apt.title
                appointmentDescriptionDetail.text = apt.description
                appointmentDateDetail.text = apt.date
                appointmentTimeDetail.text = apt.time
            }
        }
    }
}
