package filo.mamdouh.weatherforecast.datastorage.local.room.savedlocation

import android.content.Context
import filo.mamdouh.weatherforecast.datastorage.local.room.AppDatabase
import filo.mamdouh.weatherforecast.models.LocationItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SavedLocationDataSourceImpl @Inject constructor(context: Context): SavedLocationDataSource {
    private val savedLocationsDao: SavedLocationsDao = AppDatabase.getInstance(context).weatherDao

    override fun insert(savedLocations: LocationItem): Flow<Long> {
        return flow{
            emit(savedLocationsDao.insert(savedLocations))
        }
    }

    override fun delete(savedLocations: LocationItem): Flow<Int> {
        return flow{
            emit(savedLocationsDao.delete(savedLocations))
        }
    }

    override fun deleteAll(): Flow<Int> {
        return flow{
            emit(savedLocationsDao.deleteAll())
        }
    }

    override fun getAll(): Flow<List<LocationItem>> {
        return savedLocationsDao.getAll()
    }
}