package filo.mamdouh.weatherforecast.datastorage.network

import filo.mamdouh.weatherforecast.models.CurrentWeather
import filo.mamdouh.weatherforecast.models.WeatherForecast

interface NetworkDataSource {
    suspend fun getCurrentWeather(lat: Double, lon: Double, units: String): CurrentWeather
    suspend fun getWeatherForecast(lat: Double, lon: Double, units: String): WeatherForecast
}