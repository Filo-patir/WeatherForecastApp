package filo.mamdouh.weatherforecast.features.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import filo.mamdouh.weatherforecast.constants.SettingsConstants
import filo.mamdouh.weatherforecast.datastorage.IRepository
import filo.mamdouh.weatherforecast.datastorage.network.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDetailsViewModel @Inject constructor (private val repo: IRepository) : ViewModel() {
    private val dispatcher = Dispatchers.IO
    private var _currentWeather = MutableStateFlow<NetworkResponse>(NetworkResponse.Loading)
    val currentWeather= _currentWeather.onStart { getData() }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3600000L),NetworkResponse.Loading)
    private val _unit = MutableStateFlow("")
    val unit = _unit.onStart { getTemp() }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3600000L),"")
    private val _speed = MutableStateFlow("")
    val speed = _speed.onStart { getSpeed() }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3600000L),"")
    private fun getTemp() {
        viewModelScope.launch(dispatcher) {
            _unit.value = repo.getSettings(SettingsConstants.TEMP.toString())
        }
    }
    private fun getSpeed() {
        viewModelScope.launch(dispatcher) {
            _speed.value = repo.getSettings(SettingsConstants.TEMP.toString())
        }
    }

    private fun getData() {
        viewModelScope.launch(dispatcher) {
                repo.getWeeklyForecastFromLocal().collect{
                    Log.d("Filo", "getData: $it")
                    _currentWeather.value = NetworkResponse.Success(it)
                }
        }
    }

}