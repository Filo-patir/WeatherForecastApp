package filo.mamdouh.weatherforecast.logic.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import filo.mamdouh.weatherforecast.models.AlarmItem
import java.time.ZoneId

class AlarmSchedulerImpl (private val context: Context) : AlarmScheduler {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)
    override fun scheduleAlarm(alarmItem: AlarmItem) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("alarmItem", alarmItem)
        }
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            alarmItem.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            PendingIntent.getBroadcast(
            context,
            alarmItem.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        ))
    }

    override fun cancelAlarm(alarmItem: AlarmItem) {
        alarmManager.cancel(PendingIntent.getBroadcast(
            context,
            alarmItem.hashCode(),
            Intent(),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        ))
    }
}