package filo.mamdouh.weatherforecast.features.alarm

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import filo.mamdouh.weatherforecast.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("message") ?: return
        Log.d("Filo", "onReceive: $message")
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(context, "alarm_channel").setContentText(message)
            .setContentTitle("Alarm")
            .setSmallIcon(R.mipmap.app_icon)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        notificationManager.notify(1, notification)
    }
}