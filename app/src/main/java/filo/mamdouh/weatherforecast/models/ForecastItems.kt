package filo.mamdouh.weatherforecast.models

data class ForecastItems(
    val clouds: Clouds,
    val dt: Long,
    val main: Main,
    val pop: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)