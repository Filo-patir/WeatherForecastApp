package filo.mamdouh.weatherforecast.features.favourite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import filo.mamdouh.weatherforecast.datastorage.IRepository
import filo.mamdouh.weatherforecast.datastorage.network.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteDisplayViewModel @Inject constructor (private val repo: IRepository) : ViewModel() {
    private val dispatcher = Dispatchers.IO
    private var _currentWeather = MutableStateFlow<NetworkResponse>(NetworkResponse.Loading)
    val currentWeather= _currentWeather.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3600000L),
        NetworkResponse.Loading)
    private var _weatherForecast = MutableStateFlow<NetworkResponse>(NetworkResponse.Loading)
    val weatherForecast = _weatherForecast.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3600000L),
        NetworkResponse.Loading)

    fun getData(lat: Double, lon: Double) {
        getWeatherData(lat,lon)
        getWeatherForecast(lat,lon)
    }
    private fun getWeatherData(lat: Double, lon: Double){
        viewModelScope.launch(dispatcher) {
            Log.d("Filo", "getWeatherData: $lon & $lat")
            val value = repo.getCurrentWeather(lat,lon,"metric")
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
    private fun getWeatherForecast(lon: Double, lat: Double){
        viewModelScope.launch(dispatcher){
            val value = repo.getWeeklyForecast(lat,lon,"metric")
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