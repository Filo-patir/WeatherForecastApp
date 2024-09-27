package filo.mamdouh.weatherforecast.datastorage.local.room.alarm

import androidx.room.TypeConverter
import java.time.LocalDateTime

class TimeConverter {
    @TypeConverter
    fun toTime(time: LocalDateTime): String{
        return time.toString()
    }
    @TypeConverter
    fun fromTime(time: String): LocalDateTime{
        return LocalDateTime.parse(time)
    }
}