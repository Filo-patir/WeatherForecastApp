package filo.mamdouh.weatherforecast.features.search.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import filo.mamdouh.weatherforecast.datastorage.IRepository
import filo.mamdouh.weatherforecast.models.CurrentWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor (private val repository: IRepository) :  ViewModel() {
    private val _list = MutableStateFlow<List<CurrentWeather>>(emptyList())
    val list = _list.onStart { getData() }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(3600000L),
        emptyList()
    )
    private fun getData() {
        val list = ArrayList<CurrentWeather>()
        viewModelScope.launch(Dispatchers.IO) {
            repository.getSavedLocations().collect {
                for (location in it){
                    val value = repository.getCurrentWeather(location.lat, location.lon, "metric")
                    if (value.isSuccessful) {
                        list.add(value.body()!!)
                    }
                }
                Log.d("Filo", "getData: $list")
                _list.emit(list)
            }
        }
    }
}