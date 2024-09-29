package filo.mamdouh.weatherforecast.datastorage.local.objectbox

import filo.mamdouh.weatherforecast.models.CachedData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class FakeBoxes : IBoxes {
    override suspend fun putWeatherForecast(
        weatherForecast: CachedData,
        dispatcher: CoroutineDispatcher
    ): Long {
        TODO("Not yet implemented")
    }

    override fun getWeatherForecast(): Flow<MutableList<CachedData>> {
        TODO("Not yet implemented")
    }
}