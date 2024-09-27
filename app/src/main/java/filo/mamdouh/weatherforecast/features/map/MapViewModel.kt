package filo.mamdouh.weatherforecast.features.map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import filo.mamdouh.weatherforecast.datastorage.IRepository
import filo.mamdouh.weatherforecast.datastorage.network.NetworkResponse
import filo.mamdouh.weatherforecast.models.LocationItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val repository: IRepository) : ViewModel() {
    private var _locationList = MutableStateFlow<NetworkResponse>(NetworkResponse.Loading)
    val locationList = _locationList.stateIn(viewModelScope, SharingStarted.Lazily, NetworkResponse.Loading)
    private var _suggestionList = MutableStateFlow<NetworkResponse>(NetworkResponse.Loading)
    val suggestionList = _suggestionList.stateIn(viewModelScope, SharingStarted.Lazily, NetworkResponse.Loading)

    fun getCoordinates(name : String) {
        viewModelScope.launch {
            val value = repository.getLocationByName(name)
            if (value.isSuccessful)
                _locationList.value = NetworkResponse.Success(value.body())
            else
                _locationList.value = NetworkResponse.Failure(value.message())
        }
    }

    fun getLocation(lat: Double, lon: Double){
        viewModelScope.launch {
            val value = repository.getLocationByCoordinates(lat, lon)
            if (value.isSuccessful)
                _locationList.value = NetworkResponse.Success(value.body())
            else
                _locationList.value = NetworkResponse.Failure(value.message())
        }
    }

    fun saveLocation(savedLocation: LocationItem?) {

        if (savedLocation == null)
            return
        val dispatcher = Dispatchers.IO
        viewModelScope.launch(dispatcher) {
            try {
            repository.insertSavedLocation(savedLocation).collect{}
            } catch (e: Exception) {
                Log.d("Filo", "saveLocation: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    fun getSuggestions(name: String) {
        viewModelScope.launch {
            val value = repository.getLocationByName(name , 5)
            if (value.isSuccessful)
                _suggestionList.value = NetworkResponse.Success(value.body())
            else
                _suggestionList.value = NetworkResponse.Failure(value.message())
        }
    }

}