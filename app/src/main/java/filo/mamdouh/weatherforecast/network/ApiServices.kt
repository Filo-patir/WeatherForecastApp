package filo.mamdouh.weatherforecast.network

import filo.mamdouh.weatherforecast.models.WeatherForecast
import retrofit2.http.GET

interface ApiServices {
    @GET
    suspend fun getWeatherForecast(lat: Double, lon: Double): WeatherForecast
}