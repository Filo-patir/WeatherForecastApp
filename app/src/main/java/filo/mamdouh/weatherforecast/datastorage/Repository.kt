package filo.mamdouh.weatherforecast.datastorage

import filo.mamdouh.weatherforecast.datastorage.network.NetworkDataSource
import filo.mamdouh.weatherforecast.datastorage.network.NetworkDataSourceImpl
import filo.mamdouh.weatherforecast.models.CurrentWeather
import filo.mamdouh.weatherforecast.models.WeatherForecast

object Repository {
    private val networkDataSourse : NetworkDataSource = NetworkDataSourceImpl()
    suspend fun getCurrentWeather(lat: Double, lon: Double , unit: String): CurrentWeather {
        return networkDataSourse.getCurrentWeather(lat, lon, unit)
    }
    suspend fun getWeeklyForecast(lat: Double, lon: Double , unit: String): WeatherForecast {
        return networkDataSourse.getWeatherForecast(lat, lon, unit)
    }
}