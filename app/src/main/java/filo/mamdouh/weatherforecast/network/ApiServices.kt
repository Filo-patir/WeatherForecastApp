package filo.mamdouh.weatherforecast.network

import retrofit2.http.GET

interface ApiServices {
    @GET
    suspend fun getWeatherData(lat: Double, lon: Double): WeatherData
}