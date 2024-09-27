package filo.mamdouh.weatherforecast.datastorage.local.room.savedlocation

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import filo.mamdouh.weatherforecast.models.LocationItem
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedLocationsDao {
    @Query("SELECT * FROM saved_locations")
    fun getAll(): Flow<List<LocationItem>>
    @Query("DELETE FROM saved_locations")
    fun deleteAll() : Int
    @Delete
    fun delete(savedLocations: LocationItem) : Int
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(savedLocations: LocationItem) : Long
}
