package filo.mamdouh.weatherforecast.features.alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import filo.mamdouh.weatherforecast.datastorage.IRepository
import filo.mamdouh.weatherforecast.models.AlarmItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor (private val repository: IRepository) : ViewModel() {
    private val dispatcher = Dispatchers.IO
    private val _alarmList = MutableStateFlow<List<AlarmItem>>(emptyList())
    val alarmList = _alarmList.onStart { getData() }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        emptyList())

    private fun getData(){
        viewModelScope.launch(dispatcher) {
            repository.getAlarm().map { items -> items.filter { it.time> LocalDateTime.now() && it.flag } }.collect{
                _alarmList.emit(it)
            }
        }
    }
    fun saveAlarm(alarmItem: AlarmItem){
        viewModelScope.launch(dispatcher) {
            repository.insertAlarm(alarmItem)
        }
    }

    fun removeAlarm(alarmItem: AlarmItem) {
        viewModelScope.launch(dispatcher) {
            repository.deleteAlarm(alarmItem)
        }
    }
}