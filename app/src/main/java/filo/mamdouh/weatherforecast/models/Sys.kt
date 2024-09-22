package filo.mamdouh.weatherforecast.models

data class Sys(
    val country: String ="",
    val id: Int = 0,
    val sunrise: Long =0L,
    val sunset: Long = 0L,
    val type: Int = 0
)