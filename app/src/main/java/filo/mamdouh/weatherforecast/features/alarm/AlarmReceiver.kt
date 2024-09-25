package filo.mamdouh.weatherforecast.features.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("message") ?: return
        Log.d("Filo", "onReceive: $message")
        val workRequest = OneTimeWorkRequestBuilder<AlarmWorker>().setInputData(workDataOf("message" to message)).build()
        WorkManager.getInstance(context!!).enqueue(workRequest)
    }
}