package filo.mamdouh.weatherforecast.datastorage.local.room

import filo.mamdouh.weatherforecast.models.SavedLocations
import kotlinx.coroutines.flow.Flow

interface SavedLocationDataSource {
    fun insert(savedLocations: SavedLocations): Flow<Long>
    fun delete(savedLocations: SavedLocations) :   Flow<Int>
    fun deleteAll() :   Flow<Int>
    fun getAll(): Flow<List<SavedLocations>>
}