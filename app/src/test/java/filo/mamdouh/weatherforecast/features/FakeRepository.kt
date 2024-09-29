package filo.mamdouh.weatherforecast.features

import filo.mamdouh.weatherforecast.datastorage.IRepository
import filo.mamdouh.weatherforecast.models.AlarmItem
import filo.mamdouh.weatherforecast.models.CachedData
import filo.mamdouh.weatherforecast.models.CurrentWeather
import filo.mamdouh.weatherforecast.models.Location
import filo.mamdouh.weatherforecast.models.LocationItem
import filo.mamdouh.weatherforecast.models.SearchRoot
import filo.mamdouh.weatherforecast.models.WeatherForecast
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class FakeRepository : IRepository {
    val list = mutableListOf(LocationItem(1, "name", 0.0, 0.0, "test",true),LocationItem(2, "name2", 0.0, 0.0, "test", false))
    var local = "en"
    override fun saveSettings(name: String, value: String) {
        local = value
    }

    override fun getSettings(name: String): Flow<String> {
        return flow { emit(local) }
    }

    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        unit: String
    ): Response<CurrentWeather> {
        return Response.success(CurrentWeather())
    }

    override suspend fun getWeeklyForecast(
        lat: Double,
        lon: Double,
        unit: String
    ): Response<WeatherForecast> {
        TODO("Not yet implemented")
    }

    override suspend fun getLocationByName(name: String, limit: Int): Response<SearchRoot> {
        TODO("Not yet implemented")
    }

    override suspend fun getLocationByCoordinates(lat: Double, lon: Double): Response<Location> {
        TODO("Not yet implemented")
    }

    override fun getSavedLocations(): Flow<List<LocationItem>> {
        return flow { emit(list) }
    }

    override fun insertSavedLocation(savedLocations: LocationItem): Flow<Long> {
        TODO("Not yet implemented")
    }

    override fun deleteSavedLocation(savedLocations: LocationItem): Flow<Int> {
        list.remove(savedLocations)
        return flow { emit(1) }
    }

    override fun deleteAllSavedLocations(): Flow<Int> {
        TODO("Not yet implemented")
    }

    override fun getWeeklyForecastFromLocal(): Flow<List<CachedData>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveWeeklyForecast(
        weatherForecast: CachedData,
        dispatcher: CoroutineDispatcher
    ) {
        TODO("Not yet implemented")
    }

    override fun getAlarm(): Flow<List<AlarmItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertAlarm(alarmItem: AlarmItem) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAlarm(alarmItem: AlarmItem) {
        TODO("Not yet implemented")
    }
}