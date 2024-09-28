package filo.mamdouh.weatherforecast

import android.util.Log
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
class MainViewModel @Inject constructor(private val repository: IRepository) : ViewModel() {
    private val _localization = MutableStateFlow<String>("en")
    val localization = _localization.onStart { getLocalization() }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        "en")

    fun getLocalization(){
        viewModelScope.launch {
            repository.getSettings(SettingsConstants.LANGUAGE.toString()).collect{
                Log.d("Filo", "getLocalization: $it")
                _localization.emit(it)
            }
        }
    }
}