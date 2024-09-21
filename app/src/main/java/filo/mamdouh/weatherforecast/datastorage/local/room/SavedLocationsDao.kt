package filo.mamdouh.weatherforecast.datastorage.local.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import filo.mamdouh.weatherforecast.models.SavedLocations
import kotlinx.coroutines.flow.Flow

interface SavedLocationsDao {
    @Query("SELECT * FROM saved_locations")
    suspend fun getAll(): Flow<List<SavedLocations>>
    @Query("DELETE FROM saved_locations")
    suspend fun deleteAll() : Flow<Int>
    @Delete
    suspend fun delete(savedLocations: SavedLocations) : Flow<Int>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(savedLocations: SavedLocations) : Flow<Long>
}
