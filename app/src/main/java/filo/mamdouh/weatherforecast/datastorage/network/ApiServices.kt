package filo.mamdouh.weatherforecast.datastorage.network

import filo.mamdouh.weatherforecast.BuildConfig
import filo.mamdouh.weatherforecast.models.CurrentWeather
import filo.mamdouh.weatherforecast.models.Location
import filo.mamdouh.weatherforecast.models.SearchRoot
import filo.mamdouh.weatherforecast.models.WeatherForecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

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
    @GET
    suspend fun getLocationByName(@Url url: String = "https://api.api-ninjas.com/v1/city/", @Query("name") name: String, @Query("limit") limit: Int, @Query("X-Api-Key") appid: String = BuildConfig.MAP_API_KEY): Response<SearchRoot>
    @GET("geo/1.0/reverse")
    suspend fun getLocationByCoordinates(@Query("lat") lat: Double, @Query("lon") lon: Double, @Query("appid") appid: String = BuildConfig.API_KEY): Response<Location>
}