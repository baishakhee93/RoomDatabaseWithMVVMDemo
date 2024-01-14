package com.baishakhee.roomdatabasedemo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.TimePicker
import java.text.SimpleDateFormat
import java.util.Calendar

class Constant {

    companion object {
        private val calendar = Calendar.getInstance()

        fun showDatePicker(dateSetter: (String) -> Unit, context: Context) {
            val datePickerDialog = DatePickerDialog(
                context,
                { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    // Update ViewModel with selected date
                    val dateFormat = SimpleDateFormat("dd-MM-yyyy")
                    val selectedDate = dateFormat.format(calendar.time)
                    dateSetter(selectedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

         fun showTimePicker(timeSetter: (String) -> Unit, context: Context) {
            val timePickerDialog = TimePickerDialog(
                context,
                { _: TimePicker, hourOfDay: Int, minute: Int ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)

                    // Update ViewModel with selected time
                    val timeFormat = SimpleDateFormat("hh:mm a")
                    val selectedTime = timeFormat.format(calendar.time)
                    timeSetter(selectedTime)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
            )
            timePickerDialog.show()
        }

    }

}