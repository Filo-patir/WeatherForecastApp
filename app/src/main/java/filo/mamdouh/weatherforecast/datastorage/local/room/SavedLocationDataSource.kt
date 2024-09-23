package filo.mamdouh.weatherforecast.datastorage.local.room

import filo.mamdouh.weatherforecast.models.LocationItem
import kotlinx.coroutines.flow.Flow

interface SavedLocationDataSource {
    fun insert(savedLocations: LocationItem): Flow<Long>
    fun delete(savedLocations: LocationItem) :   Flow<Int>
    fun deleteAll() :   Flow<Int>
    fun getAll(): Flow<List<LocationItem>>
}