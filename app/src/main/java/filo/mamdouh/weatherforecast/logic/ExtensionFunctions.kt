package filo.mamdouh.weatherforecast.logic

import filo.mamdouh.weatherforecast.R
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun Long.toTime(timeZone: Int): String {
    val dateTime = Instant.ofEpochSecond(this).atZone(ZoneOffset.ofTotalSeconds(timeZone)).toLocalDateTime()
    return DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a").format(dateTime)
}

fun Long.toHourMinute(timeZone: Int): String {
    val dateTime = Instant.ofEpochSecond(this).atZone(ZoneOffset.ofTotalSeconds(timeZone)).toLocalDateTime()
    return DateTimeFormatter.ofPattern("hh:mm").format(dateTime)
}

fun Long.toHour(timeZone: Int): String {
    val dateTime = Instant.ofEpochSecond(this).atZone(ZoneOffset.ofTotalSeconds(timeZone)).toLocalDateTime()
    return DateTimeFormatter.ofPattern("hh:00").format(dateTime)
}

fun Long.toDay(timeZone: Int):String{
    val dateTime = Instant.ofEpochSecond(this).atZone(ZoneOffset.ofTotalSeconds(timeZone)).toLocalDateTime()
    return dateTime.dayOfWeek.getDisplayName(java.time.format.TextStyle.SHORT, java.util.Locale.ENGLISH)
}
fun String.toDrawable() : Int {
    when(this){
        "01d" -> return R.drawable.sun
        "01n" -> return R.drawable.moon
        "02d" -> return R.drawable.sun_clouds
        "02n" -> return R.drawable.moon_clouds
        "03d" -> return R.drawable.clouds
        "03n" -> return R.drawable.clouds
        "04d" -> return R.drawable.clouds
        "04n" -> return R.drawable.clouds
        "09d" -> return R.drawable.rainy
        "09n" -> return R.drawable.rainy
        "10d" -> return R.drawable.sun_clouds_rain
        "10n" -> return R.drawable.moon_clouds_rain
        "11d" -> return R.drawable.storm
        "11n" -> return R.drawable.storm
        "13d" -> return R.drawable.clouds_snow
        "13n" -> return R.drawable.clouds_snow
        "50d" -> return R.drawable.mist
        "50n" -> return R.drawable.mist
        else -> return R.drawable.ic_launcher_foreground
    }
}