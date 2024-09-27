package filo.mamdouh.weatherforecast.models

data class SearchRootItem(
    val country: String,
    val is_capital: Boolean,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val population: Int
)