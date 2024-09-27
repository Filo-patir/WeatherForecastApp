package filo.mamdouh.weatherforecast.contracts

import filo.mamdouh.weatherforecast.models.AlarmItem

interface AddAlarmListener {
    fun onAddAlarmClicked(alarmItem: AlarmItem)
}