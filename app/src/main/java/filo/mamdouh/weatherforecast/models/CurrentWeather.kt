package filo.mamdouh.weatherforecast.models

import filo.mamdouh.weatherforecast.datastorage.local.objectbox.CloudsConverter
import filo.mamdouh.weatherforecast.datastorage.local.objectbox.CoordConverter
import filo.mamdouh.weatherforecast.datastorage.local.objectbox.MainConverter
import filo.mamdouh.weatherforecast.datastorage.local.objectbox.SysConverter
import filo.mamdouh.weatherforecast.datastorage.local.objectbox.WeatherConverter
import filo.mamdouh.weatherforecast.datastorage.local.objectbox.WindConverter
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class CurrentWeather(
    @Id var uid: Long = 0,
    val base: String = "",
    @Convert(converter = CloudsConverter::class , dbType = String::class) val clouds: Clouds = Clouds(),
    val cod: Int = 0,
    @Convert(converter = CoordConverter::class , dbType = String::class) val coord: Coord = Coord(),
    val dt: Int = 0,
    val id: Int= 0,
    @Convert(converter = MainConverter::class , dbType = String::class) val main: Main = Main(),
    val name: String = "",
    @Convert(converter = SysConverter::class , dbType = String::class) val sys: Sys = Sys(),
    val timezone: Int= 0,
    val visibility: Int= 0,
    @Convert(converter = WeatherConverter::class , dbType = String::class) val weather: List<Weather> = emptyList(),
    @Convert(converter = WindConverter::class, dbType = String::class) val wind: Wind = Wind(),
    val message: String = ""
)