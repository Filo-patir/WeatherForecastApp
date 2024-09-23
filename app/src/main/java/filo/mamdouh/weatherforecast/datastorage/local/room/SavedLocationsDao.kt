package filo.mamdouh.weatherforecast.datastorage.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedLocationsDao {
    @Query("SELECT * FROM saved_locations")
    fun getAll(): Flow<List<SavedLocations>>
    @Query("DELETE FROM saved_locations")
    fun deleteAll() : Int
    @Delete
    fun delete(savedLocations: SavedLocations) : Int
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(savedLocations: SavedLocations) : Long
}
