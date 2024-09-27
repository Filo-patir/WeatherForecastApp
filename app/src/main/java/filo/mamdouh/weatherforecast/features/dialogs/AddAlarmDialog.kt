package filo.mamdouh.weatherforecast.features.dialogs

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import filo.mamdouh.weatherforecast.contracts.AddAlarmListener
import filo.mamdouh.weatherforecast.databinding.AddAlarmDialogBinding
import filo.mamdouh.weatherforecast.models.AlarmItem
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.Objects

object AddAlarmDialog {
    fun showDialog(temp: Activity?, manager: FragmentManager?, listener: AddAlarmListener) {
        val activity = temp ?: return
        manager ?: return
        val builder = AlertDialog.Builder(activity)
        val binding = AddAlarmDialogBinding.inflate(activity.layoutInflater)
        var time: LocalDateTime? = null
        builder.setView(binding.root)
        val alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        Objects.requireNonNull<Window>(alertDialog.window).setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        binding.picker.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select dates")
                    .setCalendarConstraints(CalendarConstraints.Builder().setStart(MaterialDatePicker.todayInUtcMilliseconds()).build())
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
            datePicker.show(manager, "DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                val timePicker = MaterialTimePicker.Builder().setTitleText("Select Time").build()
                timePicker.show(manager, "TimePicker")
                timePicker.addOnPositiveButtonClickListener {
                    time = LocalDateTime.of(
                        Instant.ofEpochMilli(datePicker.selection!!).atZone(ZoneId.systemDefault())
                            .toLocalDate(), LocalTime.of(timePicker.hour, timePicker.minute)
                    )
                    if (time!! < LocalDateTime.now())
                    {
                        time =null
                        Toast.makeText(activity, "Please Select Valid Time", Toast.LENGTH_SHORT).show()
                    }else binding.dateTime.setText(time.toString())
                }
            }
        }
            binding.button.setOnClickListener {
                if (binding.messageTxt.text.toString().isNotBlank() && time != null) {
                    listener.onAddAlarmClicked(
                        AlarmItem(
                            time!!,
                            binding.messageTxt.text.toString(),
                            !binding.alarm.isChecked
                        )
                    )
                    alertDialog.dismiss()
                }
                else {
                    Toast.makeText(activity, "Please Fill All Fields", Toast.LENGTH_SHORT).show()
                }
            }
        alertDialog.show()

    }
    fun showDialog(temp: Activity?, manager: FragmentManager?, listener: AddAlarmListener, alarmItem: AlarmItem) {
        val activity = temp ?: return
        manager ?: return
        val builder = AlertDialog.Builder(activity)
        val binding = AddAlarmDialogBinding.inflate(activity.layoutInflater)
        var time: LocalDateTime? = null
        builder.setView(binding.root)
        val alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        Objects.requireNonNull<Window>(alertDialog.window).setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        binding.dateTime.setText(alarmItem.time.toString())
        binding.messageTxt.setText(alarmItem.message)
        binding.alarm.isChecked = !alarmItem.flag
        binding.picker.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select dates")
                    .build()
            datePicker.show(manager, "DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                val timePicker = MaterialTimePicker.Builder().setTitleText("Select Time").build()
                timePicker.show(manager, "TimePicker")
                timePicker.addOnPositiveButtonClickListener {
                    time = LocalDateTime.of(
                        Instant.ofEpochMilli(datePicker.selection!!).atZone(ZoneId.systemDefault())
                            .toLocalDate(), LocalTime.of(timePicker.hour, timePicker.minute)
                    )
                    if (time!! < LocalDateTime.now())
                    {
                        time =null
                        Toast.makeText(activity, "Please Select Valid Time", Toast.LENGTH_SHORT).show()
                    }else binding.dateTime.setText(time.toString())
                }
            }
        }
        binding.button.setOnClickListener {
            if (binding.messageTxt.text.toString().isNotBlank() && time != null) {
                alarmItem.message = binding.messageTxt.text.toString()
                alarmItem.time = time!!
                alarmItem.flag = !binding.alarm.isChecked
                listener.onAddAlarmClicked( alarmItem)
                alertDialog.dismiss()
            }
            else {
                Toast.makeText(activity, "Please Fill All Fields", Toast.LENGTH_SHORT).show()
            }
        }
        alertDialog.show()

    }
}