package filo.mamdouh.weatherforecast.datastorage

import filo.mamdouh.weatherforecast.models.CurrentWeather
import filo.mamdouh.weatherforecast.models.Location
import filo.mamdouh.weatherforecast.models.LocationItem
import filo.mamdouh.weatherforecast.models.WeatherForecast
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface IRepository {
    fun saveSettings(name: String, value: String)
    fun getSettings(name: String): Flow<String>
    suspend fun getCurrentWeather(lat: Double, lon: Double, unit: String): Response<CurrentWeather>
    suspend fun getWeeklyForecast(lat: Double, lon: Double, unit: String): Response<WeatherForecast>
    suspend fun getLocationByName(name: String, limit: Int = 1): Response<Location>
    suspend fun getLocationByCoordinates(lat: Double, lon: Double): Response<Location>
    fun getSavedLocations(): Flow<List<LocationItem>>
    fun insertSavedLocation(savedLocations: LocationItem): Flow<Long>
    fun deleteSavedLocation(savedLocations: LocationItem): Flow<Int>
    fun deleteAllSavedLocations(): Flow<Int>
    fun getCurrentWeatherFromLocal(): Flow<List<CurrentWeather>>
    fun getWeeklyForecastFromLocal(): Flow<List<WeatherForecast>>
    suspend fun saveCurrentWeather(currentWeather: CurrentWeather, dispatcher: CoroutineDispatcher)
    suspend fun saveWeeklyForecast(weatherForecast: WeatherForecast, dispatcher: CoroutineDispatcher)
}