package filo.mamdouh.weatherforecast.features.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private fun getData() {
        viewModelScope.launch(dispatcher) {
                repo.getWeeklyForecastFromLocal().collect{
                    Log.d("Filo", "getData: $it")
                    _currentWeather.value = NetworkResponse.Success(it)
                }
        }
    }
}