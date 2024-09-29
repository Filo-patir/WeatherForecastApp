package filo.mamdouh.weatherforecast

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import filo.mamdouh.weatherforecast.constants.SettingsConstants
import filo.mamdouh.weatherforecast.datastorage.IRepository
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: IRepository) : ViewModel() {

     suspend fun getLocalization(): String{
        return viewModelScope.async {
            var localization = "en"
            repository.getSettings(SettingsConstants.LANGUAGE.toString()).collect{
                Log.d("Filo", "getLocalization: $it")
                if (it.isNotBlank())
                    localization = it
            }
            localization
        }.await()
    }
}