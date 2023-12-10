package com.ismailguler.meettime

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ismailguler.meettime.databinding.FragmentCreateMeetingBinding
import com.ismailguler.meettime.home.Meeting
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID

class CreateMeetingFragment: Fragment() {
    private var _binding: FragmentCreateMeetingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateMeetingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            etMeetingDate.setOnClickListener {
                showDatePickerDialog(it)
            }

            etMeetingTime.setOnClickListener {
                showTimePickerDialog(it)
            }

            btnCreate.setOnClickListener {
                val title = etMeetingTitle.text?.toString()
                val description = etMeetingDesc.text?.toString()
                val date = etMeetingDate.text?.toString()
                val time = etMeetingTime.text?.toString()

                if (!title.isNullOrBlank() && !description.isNullOrBlank() && !date.isNullOrBlank() && !time.isNullOrBlank()) {
                   val sh =  SharedPreferencesUtil(requireContext())
                    val meeting = Meeting(title,
                        generateSixDigitRandomUUID(),
                        sh.getCurrentUser(),
                        description,
                        date,
                        time
                    )
                    sh.saveMeeting(meeting)
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(requireContext(),"Lütfen tüm alanları doldurunuz.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun showDatePickerDialog(view: View) {
        val editText = view as EditText

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.MyDatePickerDialogTheme,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)
                editText.setText(formatDate(selectedDate.time))
            },
            year,
            month,
            day
        )

        datePickerDialog.datePicker.calendarViewShown = true
        datePickerDialog.show()
    }

    private fun formatDate(date: Date): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(date)
    }

    private fun showTimePickerDialog(view: View) {
        val editText = view as EditText
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            R.style.MyDatePickerDialogTheme,
            { _, selectedHour, selectedMinute ->
                editText.setText(
                    String.format(
                        Locale.getDefault(),
                        "%02d:%02d",
                        selectedHour,
                        selectedMinute
                    )
                )
            },
            hour,
            minute,
            true
        )

        timePickerDialog.show()
    }

    private fun generateSixDigitRandomUUID(): String = UUID.randomUUID().toString().substring(0, 6)

}