package filo.mamdouh.weatherforecast.contracts

import filo.mamdouh.weatherforecast.models.AlarmItem

interface NotificationListener {
    fun removeItem(alarmItem: AlarmItem)
}