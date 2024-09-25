package filo.mamdouh.weatherforecast.features.alarm

import filo.mamdouh.weatherforecast.models.AlarmItem

interface AlarmScheduler {
    fun scheduleAlarm(alarmItem: AlarmItem)
    fun cancelAlarm(alarmItem: AlarmItem)
}