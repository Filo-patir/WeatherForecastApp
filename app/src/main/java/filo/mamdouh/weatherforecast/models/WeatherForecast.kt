package filo.mamdouh.weatherforecast.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

data class WeatherForecast(
    @Id var id: Long = 0,
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<ForecastItems>,
    val message: Int
)