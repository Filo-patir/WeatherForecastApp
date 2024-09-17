package filo.mamdouh.weatherforecast.models

data class WeatherForecast(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<ForecastItems>,
    val message: Int
)