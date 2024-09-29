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
    val local = _local.onStart { getLocalization() }.stateIn(viewModelScope, SharingStarted.Eagerly, true)
        fun getLocalization(){
        viewModelScope.launch {
             repository.getSettings(SettingsConstants.LANGUAGE.toString()).collect{
                 _local.emit(it=="en")
             }
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