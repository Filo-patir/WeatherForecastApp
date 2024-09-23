package filo.mamdouh.weatherforecast.features.search.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import filo.mamdouh.weatherforecast.datastorage.IRepository
import filo.mamdouh.weatherforecast.datastorage.network.NetworkResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val repository: IRepository) : ViewModel() {
    private var _locationList = MutableStateFlow<NetworkResponse>(NetworkResponse.Loading)
    val locationList = _locationList.stateIn(viewModelScope, SharingStarted.Lazily, NetworkResponse.Loading)

    fun getData(name : String) {
        viewModelScope.launch {
            val value = repository.getLocationByName(name)
            if (value.isSuccessful)
                _locationList.value = NetworkResponse.Success(value.body())
            else
                _locationList.value = NetworkResponse.Failure(value.message())
        }
    }
}