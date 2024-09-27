package filo.mamdouh.weatherforecast.datastorage.local.room.alarm

import android.content.Context
import filo.mamdouh.weatherforecast.datastorage.local.room.AppDatabase
import filo.mamdouh.weatherforecast.models.AlarmItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmDataSourceImpl @Inject constructor(context: Context) : AlarmDataSource {
    private val alarmDao: AlarmDao = AppDatabase.getInstance(context).alarmDao
    override suspend fun insertAlarm(alarm: AlarmItem) {
        alarmDao.insertAlarm(alarm)
    }

    override suspend fun deleteAlarm(alarm: AlarmItem) {
        alarmDao.deleteAlarm(alarm)
    }

    override fun getAllAlarms(): Flow<List<AlarmItem>> {
        return alarmDao.getAllAlarms()
    }
}