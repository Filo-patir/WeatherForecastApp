package filo.mamdouh.weatherforecast.features.search.main

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import filo.mamdouh.weatherforecast.datastorage.IRepository
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor (val repository: IRepository) :  ViewModel() {
    init {
        Log.d("Filo", "Im view Model: ")
    }
}