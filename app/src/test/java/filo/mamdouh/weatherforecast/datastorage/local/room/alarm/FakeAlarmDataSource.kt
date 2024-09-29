package filo.mamdouh.weatherforecast.datastorage.local.room.alarm

import filo.mamdouh.weatherforecast.models.AlarmItem
import kotlinx.coroutines.flow.Flow

class FakeAlarmDataSource : AlarmDataSource {
    override suspend fun insertAlarm(alarm: AlarmItem) {
    }

    override suspend fun deleteAlarm(alarm: AlarmItem) {
    }

    override fun getAllAlarms(): Flow<List<AlarmItem>> {
        TODO("Not yet implemented")
    }
}