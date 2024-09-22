package filo.mamdouh.weatherforecast.datastorage

import filo.mamdouh.weatherforecast.datastorage.local.room.SavedLocationDataSource
import filo.mamdouh.weatherforecast.datastorage.network.NetworkDataSource
import filo.mamdouh.weatherforecast.models.CurrentWeather
import filo.mamdouh.weatherforecast.models.SavedLocations
import filo.mamdouh.weatherforecast.models.WeatherForecast
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val localDataSource: SavedLocationDataSource , private val networkDataSource: NetworkDataSource) :
    IRepository {
    override suspend fun getCurrentWeather(lat: Double, lon: Double, unit: String): Response<CurrentWeather> {
        return networkDataSource.getCurrentWeather(lat, lon, unit)
    }
    override suspend fun getWeeklyForecast(lat: Double, lon: Double, unit: String): Response<WeatherForecast> {
        return networkDataSource.getWeatherForecast(lat, lon, unit)
    }
    override fun getSavedLocations(): Flow<List<SavedLocations>> {
        return localDataSource.getAll()
    }
    override fun insertSavedLocation(savedLocations: SavedLocations): Flow<Long> {
        return localDataSource.insert(savedLocations)
    }
    override fun deleteSavedLocation(savedLocations: SavedLocations): Flow<Int> {
        return localDataSource.delete(savedLocations)
    }
    override fun deleteAllSavedLocations(): Flow<Int> {
        return localDataSource.deleteAll()
    }
//    companion object{
//        private var instance: Repository? = null
//        fun getInstance(localDataSource: SavedLocationDataSource , networkDataSource: NetworkDataSource): Repository{
//            return instance ?: run {
//                val temp = Repository(localDataSource,networkDataSource)
//                instance = temp
//                temp
//            }
//        }
//    }
}