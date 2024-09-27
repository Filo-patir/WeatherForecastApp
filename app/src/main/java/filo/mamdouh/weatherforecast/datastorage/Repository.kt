package filo.mamdouh.weatherforecast.datastorage

import filo.mamdouh.weatherforecast.datastorage.local.objectbox.Boxes
import filo.mamdouh.weatherforecast.datastorage.local.room.SavedLocationDataSource
import filo.mamdouh.weatherforecast.datastorage.local.sharedpref.ISharedPreferencesHandler
import filo.mamdouh.weatherforecast.datastorage.network.NetworkDataSource
import filo.mamdouh.weatherforecast.models.CurrentWeather
import filo.mamdouh.weatherforecast.models.Location
import filo.mamdouh.weatherforecast.models.LocationItem
import filo.mamdouh.weatherforecast.models.WeatherForecast
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val localDataSource: SavedLocationDataSource , private val networkDataSource: NetworkDataSource, private val sharedPreferencesHandler: ISharedPreferencesHandler) :
    IRepository {
        private val boxes = Boxes()
    override fun saveSettings(name: String, value: String) {
        sharedPreferencesHandler.save(name, value)
    }

    override fun getSettings(name: String): Flow<String> {
        return sharedPreferencesHandler.get(name)
    }

    override suspend fun getCurrentWeather(lat: Double, lon: Double, unit: String): Response<CurrentWeather> {
        return networkDataSource.getCurrentWeather(lat, lon, unit)
    }
    override suspend fun getWeeklyForecast(lat: Double, lon: Double, unit: String): Response<WeatherForecast> {
        return networkDataSource.getWeatherForecast(lat, lon, unit)
    }

    override suspend fun getLocationByName(name: String, limit: Int): Response<Location> {
        return networkDataSource.getLocationByName(name, limit)
    }

    override suspend fun getLocationByCoordinates(lat: Double, lon: Double): Response<Location> {
        return networkDataSource.getLocationByCoordinates(lat, lon)
    }

    override fun getSavedLocations(): Flow<List<LocationItem>> {
        return localDataSource.getAll()
    }
    override fun insertSavedLocation(savedLocations: LocationItem): Flow<Long> {
        return localDataSource.insert(savedLocations)
    }
    override fun deleteSavedLocation(savedLocations: LocationItem): Flow<Int> {
        return localDataSource.delete(savedLocations)
    }
    override fun deleteAllSavedLocations(): Flow<Int> {
        return localDataSource.deleteAll()
    }

    override fun getCurrentWeatherFromLocal(): Flow<List<CurrentWeather>> {
        return boxes.getCurrentWeather()
    }

    override fun getWeeklyForecastFromLocal(): Flow<List<WeatherForecast>> {
        return boxes.getWeatherForecast()
    }
    override suspend fun saveCurrentWeather(currentWeather: CurrentWeather, dispatcher: CoroutineDispatcher) {
        boxes.putCurrentWeather(currentWeather, dispatcher)
    }
    override suspend fun saveWeeklyForecast(weatherForecast: WeatherForecast, dispatcher: CoroutineDispatcher) {
        boxes.putWeatherForecast(weatherForecast, dispatcher)
    }

}