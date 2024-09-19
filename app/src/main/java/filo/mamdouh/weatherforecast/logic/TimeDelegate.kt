package filo.mamdouh.weatherforecast.logic

import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class TimeDelegate {
    operator fun getValue(thisRef: Any?, dt: Any?, timezone: Int): String {
        val dateTime = Instant.ofEpochSecond(dt as Long).atZone(ZoneOffset.ofTotalSeconds(timezone)).toLocalDateTime()
        return DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a").format(dateTime)
    }
}