package filo.mamdouh.weatherforecast.datastorage.local.room.alarm

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import filo.mamdouh.weatherforecast.models.AlarmItem
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {
    @Query("SELECT * FROM alarm_table")
    fun getAllAlarms(): Flow<List<AlarmItem>>
    @Delete
    suspend fun deleteAlarm(alarmItem: AlarmItem) : Int
    @Insert
    suspend fun insertAlarm(alarmItem: AlarmItem) : Long
}