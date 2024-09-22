package filo.mamdouh.weatherforecast.datastorage.network

import filo.mamdouh.weatherforecast.models.CurrentWeather
import filo.mamdouh.weatherforecast.models.WeatherForecast
import retrofit2.Response

interface NetworkDataSource {
    suspend fun getCurrentWeather(lat: Double, lon: Double, units: String): Response<CurrentWeather>
    suspend fun getWeatherForecast(lat: Double, lon: Double, units: String): Response<WeatherForecast>
}