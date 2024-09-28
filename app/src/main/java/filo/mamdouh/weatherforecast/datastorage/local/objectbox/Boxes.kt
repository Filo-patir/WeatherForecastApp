package filo.mamdouh.weatherforecast.datastorage.local.objectbox

import filo.mamdouh.weatherforecast.models.CachedData
import io.objectbox.kotlin.boxFor
import io.objectbox.kotlin.toFlow
import io.objectbox.query.Query
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class Boxes {
    private val weatherForecastBox = ObjectBox.store.boxFor(CachedData::class)

    suspend fun putWeatherForecast(weatherForecast: CachedData , dispatcher: CoroutineDispatcher) = withContext(dispatcher){
        weatherForecastBox.put(weatherForecast)
    }
    @OptIn(ExperimentalCoroutinesApi::class)
    fun getWeatherForecast(): Flow<MutableList<CachedData>> {
        val query: Query<CachedData> = weatherForecastBox.query().build()
        return query.subscribe().toFlow()
    }
}