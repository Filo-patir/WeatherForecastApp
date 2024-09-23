package filo.mamdouh.weatherforecast.datastorage

import filo.mamdouh.weatherforecast.models.CurrentWeather
import filo.mamdouh.weatherforecast.models.Location
import filo.mamdouh.weatherforecast.models.WeatherForecast
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface IRepository {
    suspend fun getCurrentWeather(lat: Double, lon: Double, unit: String): Response<CurrentWeather>
    suspend fun getWeeklyForecast(lat: Double, lon: Double, unit: String): Response<WeatherForecast>
    suspend fun getLocationByName(name: String): Response<Location>
    fun getSavedLocations(): Flow<List<SavedLocations>>
    fun insertSavedLocation(savedLocations: SavedLocations): Flow<Long>
    fun deleteSavedLocation(savedLocations: SavedLocations): Flow<Int>
    fun deleteAllSavedLocations(): Flow<Int>
}