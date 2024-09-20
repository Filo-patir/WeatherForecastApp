package filo.mamdouh.weatherforecast.datastorage.network

import filo.mamdouh.weatherforecast.BuildConfig
import filo.mamdouh.weatherforecast.models.CurrentWeather
import filo.mamdouh.weatherforecast.models.WeatherForecast
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("weather")
suspend fun getCurrentWeather(@Query("lat") lat: Double, @Query("lon") lon: Double, @Query("units") units: String, @Query("appid") appid: String = BuildConfig.API_KEY): CurrentWeather
    @GET("forecast")
    suspend fun getWeatherForecast(@Query("lat") lat: Double, @Query("lon") lon: Double, @Query("units") units: String, @Query("appid") appid: String = BuildConfig.API_KEY): WeatherForecast
}