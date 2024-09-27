package filo.mamdouh.weatherforecast.features.alarm

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import filo.mamdouh.weatherforecast.R
import filo.mamdouh.weatherforecast.models.AlarmItem

class AlarmReceiver : BroadcastReceiver() {
    private lateinit var alarmItem : AlarmItem
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onReceive(context: Context, intent: Intent) {
        alarmItem = intent.getParcelableExtra("alarmItem", AlarmItem::class.java) ?: return
        if (alarmItem.flag){
            Log.d("Filo", "onReceive: ${alarmItem.message}")
            showNotification(context)
            if (Settings.canDrawOverlays(context)){
                showAlarm(context)
            }
        }
        else showNotification(context)
    }
    private fun showNotification(context: Context){
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(context, "alarm_channel").setContentText(alarmItem.message)
            .setContentTitle("Alarm")
            .setSmallIcon(R.mipmap.app_icon)
            .addAction(R.drawable.baseline_notifications_24, "Dismiss", null)
            .build()
        notificationManager.notify(alarmItem.id.toInt(), notification)
    }
    private fun showAlarm(context: Context){
        val intent = Intent(context, OverlayServices::class.java)
        intent.putExtra("alarmItem", alarmItem)
        context.startService(intent)
    }
}