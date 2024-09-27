package filo.mamdouh.weatherforecast.datastorage.local.room.alarm

import filo.mamdouh.weatherforecast.models.AlarmItem
import kotlinx.coroutines.flow.Flow

interface AlarmDataSource {
    suspend fun insertAlarm(alarm: AlarmItem)
    suspend fun deleteAlarm(alarm: AlarmItem)
    fun getAllAlarms(): Flow<List<AlarmItem>>
}