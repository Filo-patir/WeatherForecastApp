package filo.mamdouh.weatherforecast.features.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import filo.mamdouh.weatherforecast.constants.SettingsConstants
import filo.mamdouh.weatherforecast.datastorage.IRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: IRepository): ViewModel() {
    private val _local = MutableStateFlow(true)
    val local = _local.onStart { getLocalization() }.stateIn(viewModelScope, SharingStarted.Lazily, true)
    private val _unit = MutableStateFlow("C")
    val unit = _unit.onStart { getUnit() }.stateIn(viewModelScope, SharingStarted.Lazily, "C")
    private val _speed = MutableStateFlow(true)
    val speed = _speed.onStart { getWinSpeed() }.stateIn(viewModelScope, SharingStarted.Lazily, true)
    private val _location = MutableStateFlow(true)
    val location = _location.onStart { getLocation() }.stateIn(viewModelScope, SharingStarted.Lazily, true)
        fun getLocalization(){
        viewModelScope.launch {
             _local.value = repository.getSettings(SettingsConstants.LANGUAGE.toString()) == "en"
        }
    }
    private fun getUnit(){
        viewModelScope.launch {
            _unit.value = repository.getSettings(SettingsConstants.TEMP.toString())
        }
    }
    private fun getWinSpeed(){
        viewModelScope.launch {
            _speed.value = repository.getSettings(SettingsConstants.SPEED.toString()) == "mps"
        }
    }
    private fun getLocation(){
        viewModelScope.launch {
            _location.value = repository.getSettings(SettingsConstants.LOCATION.toString()) == "gps"
        }
    }
    fun setLocalization(locale: String){
        repository.saveSettings(SettingsConstants.LANGUAGE.toString(), locale)
    }
    fun setUnit(unit: String){
        repository.saveSettings(SettingsConstants.TEMP.toString(),unit)
    }
    fun setWindSpeed(speed: String){
        repository.saveSettings(SettingsConstants.SPEED.toString(),speed)
    }
    fun setLocation(flag: Boolean){
        repository.saveSettings(SettingsConstants.LOCATION.toString(),"$flag")
    }
}