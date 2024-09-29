package filo.mamdouh.weatherforecast.datastorage.network

import filo.mamdouh.weatherforecast.models.CurrentWeather
import filo.mamdouh.weatherforecast.models.Location
import filo.mamdouh.weatherforecast.models.SearchRoot
import filo.mamdouh.weatherforecast.models.WeatherForecast
import retrofit2.Response

class FakeNetworkDataSource : NetworkDataSource {
    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        units: String
    ): Response<CurrentWeather> {
        TODO("Not yet implemented")
    }

    override suspend fun getWeatherForecast(
        lat: Double,
        lon: Double,
        units: String
    ): Response<WeatherForecast> {
        TODO("Not yet implemented")
    }

    override suspend fun getLocationByName(name: String, limit: Int): Response<SearchRoot> {
        TODO("Not yet implemented")
    }

    override suspend fun getLocationByCoordinates(lat: Double, lon: Double): Response<Location> {
        TODO("Not yet implemented")
    }
}