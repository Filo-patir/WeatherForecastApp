package filo.mamdouh.weatherforecast.contracts

import filo.mamdouh.weatherforecast.models.AlarmItem

interface AlarmListener {
    fun onRemoveCLicked(alarmItem: AlarmItem)
    fun onEditClicked(alarmItem: AlarmItem)
}