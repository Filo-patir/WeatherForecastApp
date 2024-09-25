package filo.mamdouh.weatherforecast.features.alarm

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import filo.mamdouh.weatherforecast.R

class AlarmWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    private var message : String? = inputData.getString("message")
    override fun doWork(): Result {
        // Create a notification
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(applicationContext, "alarm_channel").setContentText(message)
            .setContentTitle("Alarm")
            .setSmallIcon(R.mipmap.app_icon)
            .build()
        notificationManager.notify(1, notification)
        return Result.success()
    }
}