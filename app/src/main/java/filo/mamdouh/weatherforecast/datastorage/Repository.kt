package filo.mamdouh.weatherforecast.datastorage

import filo.mamdouh.weatherforecast.datastorage.local.objectbox.Boxes
import filo.mamdouh.weatherforecast.datastorage.local.room.alarm.AlarmDataSource
import filo.mamdouh.weatherforecast.datastorage.local.room.savedlocation.SavedLocationDataSource
import filo.mamdouh.weatherforecast.datastorage.local.sharedpref.ISharedPreferencesHandler
import filo.mamdouh.weatherforecast.datastorage.network.NetworkDataSource
import filo.mamdouh.weatherforecast.models.AlarmItem
import filo.mamdouh.weatherforecast.models.CachedData
import filo.mamdouh.weatherforecast.models.CurrentWeather
import filo.mamdouh.weatherforecast.models.Location
import filo.mamdouh.weatherforecast.models.LocationItem
import filo.mamdouh.weatherforecast.models.SearchRoot
import filo.mamdouh.weatherforecast.models.WeatherForecast
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: SavedLocationDataSource,
    private val networkDataSource: NetworkDataSource,
    private val sharedPreferencesHandler: ISharedPreferencesHandler,
    private val alarmDataSource: AlarmDataSource
) :
    IRepository {
        private val boxes = Boxes()

    //  * Settings Functions
    override fun saveSettings(name: String, value: String) {
        sharedPreferencesHandler.save(name, value)
    }

    override fun getSettings(name: String): Flow<String> {
        return sharedPreferencesHandler.get(name)
    }


    //  * Api Functions
    override suspend fun getCurrentWeather(lat: Double, lon: Double, unit: String): Response<CurrentWeather> {
        return networkDataSource.getCurrentWeather(lat, lon, unit)
    }
    override suspend fun getWeeklyForecast(lat: Double, lon: Double, unit: String): Response<WeatherForecast> {
        return networkDataSource.getWeatherForecast(lat, lon, unit)
    }

    override suspend fun getLocationByName(name: String, limit: Int): Response<SearchRoot> {
        return networkDataSource.getLocationByName(name, limit)
    }

    override suspend fun getLocationByCoordinates(lat: Double, lon: Double): Response<Location> {
        return networkDataSource.getLocationByCoordinates(lat, lon)
    }



    //  * SavedLocation Functions
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


    //  * ObjectBox Functions
    override fun getWeeklyForecastFromLocal(): Flow<List<CachedData>> {
        return boxes.getWeatherForecast()
    }
    override suspend fun saveWeeklyForecast(weatherForecast: CachedData, dispatcher: CoroutineDispatcher) {
        boxes.putWeatherForecast(weatherForecast, dispatcher)
    }


    //  * Alarm Functions
    override fun getAlarm(): Flow<List<AlarmItem>> {
        return alarmDataSource.getAllAlarms()
    }

    override suspend fun insertAlarm(alarmItem: AlarmItem) {
        alarmDataSource.insertAlarm(alarmItem)
    }

    override suspend fun deleteAlarm(alarmItem: AlarmItem) {
        alarmDataSource.deleteAlarm(alarmItem)
    }

}