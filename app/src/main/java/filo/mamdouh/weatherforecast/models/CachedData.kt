package filo.mamdouh.weatherforecast.models

import filo.mamdouh.weatherforecast.datastorage.local.objectbox.CachedCompositeKeyConverter
import filo.mamdouh.weatherforecast.datastorage.local.objectbox.CloudsConverter
import filo.mamdouh.weatherforecast.datastorage.local.objectbox.MainConverter
import filo.mamdouh.weatherforecast.datastorage.local.objectbox.SysConverter
import filo.mamdouh.weatherforecast.datastorage.local.objectbox.WeatherConverter
import filo.mamdouh.weatherforecast.datastorage.local.objectbox.WindConverter
import io.objectbox.annotation.ConflictStrategy
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Unique

@Entity
data class CachedData(
    @Id var id: Long,
    @Unique(onConflict = ConflictStrategy.REPLACE) @Convert(converter = CachedCompositeKeyConverter::class, dbType = String::class) val key: CachedCompositeKey,
    @Convert(converter = CloudsConverter::class, dbType = String::class) val clouds: Clouds,
    @Convert(converter = MainConverter::class, dbType = String::class) val main: Main,
    @Convert(converter = SysConverter::class, dbType = String::class) val sys: Sys,
    val timeZone: Int,
    val visibility: Int,
    @Convert(converter = WeatherConverter::class, dbType = String::class) val weather: List<Weather>,
    @Convert(converter = WindConverter::class, dbType = String::class) val wind: Wind,
)