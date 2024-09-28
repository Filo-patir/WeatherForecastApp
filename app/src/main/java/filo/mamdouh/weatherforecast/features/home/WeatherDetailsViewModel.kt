package filo.mamdouh.weatherforecast.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import filo.mamdouh.weatherforecast.datastorage.IRepository
import filo.mamdouh.weatherforecast.datastorage.network.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDetailsViewModel @Inject constructor (private val repo: IRepository) : ViewModel() {
    private val dispatcher = Dispatchers.IO
    private var _currentWeather = MutableStateFlow<NetworkResponse>(NetworkResponse.Loading)
    val currentWeather= _currentWeather.onStart { getData() }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3600000L),NetworkResponse.Loading)
    private var _weatherForecast = MutableStateFlow<NetworkResponse>(NetworkResponse.Loading)
    val weatherForecast = _weatherForecast.onStart { getData() }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3600000L),NetworkResponse.Loading)

    private fun getData() {
        viewModelScope.launch(dispatcher) {
            val location = repo.getSavedLocations().map { location -> location.filter { it.home } }.single()[0]
            repo.getWeeklyForecastFromLocal().map { cached -> cached.filter { it.key.city.name == location.name} }.collect{
                _currentWeather.value = NetworkResponse.Success(it)
            }
        }
    }
}