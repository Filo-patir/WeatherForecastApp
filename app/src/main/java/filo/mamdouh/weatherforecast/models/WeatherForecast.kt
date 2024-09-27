package filo.mamdouh.weatherforecast.models

import filo.mamdouh.weatherforecast.datastorage.local.objectbox.CityConverter
import filo.mamdouh.weatherforecast.datastorage.local.objectbox.ForeCastItemConverter
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class WeatherForecast(
    @Id var id: Long = 0,
    @Convert(converter = CityConverter::class, dbType = String::class) val city: City,
    val cnt: Int,
    val cod: String,
    @Convert(converter = ForeCastItemConverter::class, dbType = String::class) val list: List<ForecastItems>,
    val message: Int
)