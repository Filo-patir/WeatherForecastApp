package filo.mamdouh.weatherforecast.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import filo.mamdouh.weatherforecast.datastorage.IRepository
import filo.mamdouh.weatherforecast.datastorage.network.NetworkResponse
import filo.mamdouh.weatherforecast.models.LocationItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteViewModel @Inject constructor (private val repo: IRepository) : ViewModel() {
    private val dispatcher = Dispatchers.IO
    private var _currentWeather = MutableStateFlow<NetworkResponse>(NetworkResponse.Loading)
    val currentWeather= _currentWeather.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3600000L),
        NetworkResponse.Loading)
    private var _weatherForecast = MutableStateFlow<NetworkResponse>(NetworkResponse.Loading)
    val weatherForecast = _weatherForecast.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3600000L),
        NetworkResponse.Loading)

    fun getData(location : LocationItem) {
        getWeatherData(location)
        getWeatherForecast(location)
    }
    private fun getWeatherData(location: LocationItem){
        viewModelScope.launch(dispatcher) {
            val value = repo.getCurrentWeather(location.lat,location.lon,"metric")
            if (value.isSuccessful)
            {
                if (value.body()?.cod == 200 )
                    _currentWeather.value = NetworkResponse.Success(value.body())
                else
                    _currentWeather.value = NetworkResponse.Failure(value.body()?.message ?: "Error")
            }
            else
                _currentWeather.value = NetworkResponse.Failure(value.message())
        }
    }
    private fun getWeatherForecast(location: LocationItem){
        viewModelScope.launch(dispatcher){
            val value = repo.getWeeklyForecast(location.lat,location.lon,"metric")
            if (value.isSuccessful)
            {
                try {
                    _weatherForecast.value = NetworkResponse.Success(value.body())
                } catch (e: Exception) {
                    _weatherForecast.value = NetworkResponse.Failure(e.message ?: "Error")
                }
            }
            else
                _weatherForecast.value = NetworkResponse.Failure(value.message())
        }
    }
}