package filo.mamdouh.weatherforecast.features.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import filo.mamdouh.weatherforecast.datastorage.IRepository
import filo.mamdouh.weatherforecast.models.CurrentWeather
import filo.mamdouh.weatherforecast.models.LocationItem
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
class SearchViewModel @Inject constructor (private val repository: IRepository) :  ViewModel() {
    private var locationList = emptyList<LocationItem>()
    private val _list = MutableStateFlow<List<CurrentWeather>>(emptyList())
    val list = _list.onStart { getData() }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(3600000L),
        emptyList()
    )
    fun getData() {
        val wlist = ArrayList<CurrentWeather>()
        viewModelScope.launch(Dispatchers.IO) {
            repository.getSavedLocations().map { locationList -> locationList.filter { !it.home } }.collect {
                Log.d("Filo", "getData: $it")
                locationList = it
                it.forEach { item ->
                    val currentWeather = repository.getCurrentWeather(item.lat, item.lon, "metric")
                    if (currentWeather.isSuccessful) {
                        Log.d("Filo", "getData: ${currentWeather.body()}")
                        wlist.add(currentWeather.body()!!)
                    }
                }
                _list.value = wlist
                Log.d("Filo", "getData: $wlist")
            }
        }
    }

    fun deleteLocation(position: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSavedLocation(locationList[position]).single()
        }
    }
}