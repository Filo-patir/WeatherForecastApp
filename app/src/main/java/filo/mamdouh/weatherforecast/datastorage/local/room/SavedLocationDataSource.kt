package filo.mamdouh.weatherforecast.datastorage.local.room

import filo.mamdouh.weatherforecast.models.SavedLocations
import kotlinx.coroutines.flow.Flow

interface SavedLocationDataSource {
    suspend fun insert(savedLocations: SavedLocations): Flow<Long>
    suspend fun delete(savedLocations: SavedLocations) :   Flow<Int>
    suspend fun deleteAll() :   Flow<Int>
    suspend fun getAll(): Flow<List<SavedLocations>>
}