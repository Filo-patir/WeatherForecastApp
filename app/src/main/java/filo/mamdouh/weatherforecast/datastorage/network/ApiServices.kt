package filo.mamdouh.weatherforecast.datastorage.network

import filo.mamdouh.weatherforecast.BuildConfig
import filo.mamdouh.weatherforecast.models.CurrentWeather
import filo.mamdouh.weatherforecast.models.Location
import filo.mamdouh.weatherforecast.models.WeatherForecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("data/2.5/weather")
suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") appid: String = BuildConfig.API_KEY
    ): Response<CurrentWeather>
    @GET("data/2.5/forecast")
    suspend fun getWeatherForecast(@Query("lat") lat: Double, @Query("lon") lon: Double, @Query("units") units: String, @Query("appid") appid: String = BuildConfig.API_KEY): Response<WeatherForecast>
    @GET("geo/1.0/direct")
    suspend fun getLocationByName(@Query("q") name: String, @Query("limit") limit: Int, @Query("appid") appid: String = BuildConfig.API_KEY): Response<Location>
    @GET("geo/1.0/reverse")
    suspend fun getLocationByCoordinates(@Query("lat") lat: Double, @Query("lon") lon: Double, @Query("appid") appid: String = BuildConfig.API_KEY): Response<Location>
}