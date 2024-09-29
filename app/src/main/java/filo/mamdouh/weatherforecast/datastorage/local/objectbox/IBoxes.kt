package filo.mamdouh.weatherforecast.datastorage.local.objectbox

import filo.mamdouh.weatherforecast.models.CachedData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

interface IBoxes {

    suspend fun putWeatherForecast(
        weatherForecast: CachedData,
        dispatcher: CoroutineDispatcher
    ): Long

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getWeatherForecast(): Flow<MutableList<CachedData>>
}