package filo.mamdouh.weatherforecast.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import filo.mamdouh.weatherforecast.datastorage.Repository
import filo.mamdouh.weatherforecast.models.CurrentWeather
import filo.mamdouh.weatherforecast.models.WeatherForecast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherDetailsViewModel : ViewModel() {
    private val repo = Repository
    private val dispatcher = Dispatchers.IO
    private var _currentWeather = MutableLiveData<CurrentWeather>()
    val currentWeather: LiveData<CurrentWeather> = _currentWeather
    private var _weatherForecast = MutableLiveData<WeatherForecast>()
    val weatherForecast: LiveData<WeatherForecast> = _weatherForecast
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading
        .onStart { getData() }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3600000L),false)
    init{
        getData()
    }

    fun getData() {
        _isLoading.value = true
        viewModelScope.launch(dispatcher) {
            val value = repo.getCurrentWeather(31.199004,29.894378,"metric")
            withContext(Dispatchers.Main){
                _currentWeather.value = value
            }
            _isLoading.value = false
        }
        viewModelScope.launch(dispatcher){
            val value = repo.getWeeklyForecast(31.199004,29.894378,"metric")
            withContext(Dispatchers.Main){
                _weatherForecast.value = value
            }
        }
    }
}