package filo.mamdouh.weatherforecast.logic.alarm

import filo.mamdouh.weatherforecast.models.AlarmItem

interface AlarmScheduler {
    fun scheduleAlarm(alarmItem: AlarmItem)
    fun cancelAlarm(alarmItem: AlarmItem)
}