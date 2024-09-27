package filo.mamdouh.weatherforecast.datastorage.local.objectbox

import filo.mamdouh.weatherforecast.models.CurrentWeather
import filo.mamdouh.weatherforecast.models.WeatherForecast
import io.objectbox.kotlin.boxFor
import io.objectbox.kotlin.toFlow
import io.objectbox.query.Query
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class Boxes {
    private val weatherForecastBox = ObjectBox.store.boxFor(WeatherForecast::class)
    private val currentWeatherBox = ObjectBox.store.boxFor(CurrentWeather::class)

    suspend fun putWeatherForecast(weatherForecast: WeatherForecast , dispatcher: CoroutineDispatcher) = withContext(dispatcher){
        weatherForecastBox.put(weatherForecast)
    }
    suspend fun putCurrentWeather(currentWeather: CurrentWeather , dispatcher: CoroutineDispatcher) = withContext(dispatcher){
        currentWeatherBox.put(currentWeather)
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    fun getWeatherForecast(): Flow<MutableList<WeatherForecast>> {
        val query: Query<WeatherForecast> = weatherForecastBox.query().build()
        return query.subscribe().toFlow()
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    fun getCurrentWeather(): Flow<MutableList<CurrentWeather>> {
        val query: Query<CurrentWeather> = currentWeatherBox.query().build()
        return query.subscribe().toFlow()
    }
}