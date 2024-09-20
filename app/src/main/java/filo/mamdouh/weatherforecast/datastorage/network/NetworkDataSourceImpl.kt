package filo.mamdouh.weatherforecast.datastorage.network

import filo.mamdouh.weatherforecast.models.CurrentWeather
import filo.mamdouh.weatherforecast.models.WeatherForecast

class NetworkDataSourceImpl : NetworkDataSource {
    val api : ApiServices = RetrofitClient.apiServices
    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        units: String
    ): CurrentWeather {
        return api.getCurrentWeather(lat,lon,units)
    }

    override suspend fun getWeatherForecast(
        lat: Double,
        lon: Double,
        units: String
    ): WeatherForecast {
        return api.getWeatherForecast(lat,lon,units)
    }
}