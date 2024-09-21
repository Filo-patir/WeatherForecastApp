package filo.mamdouh.weatherforecast.datastorage.local.room

import android.content.Context
import filo.mamdouh.weatherforecast.models.SavedLocations
import kotlinx.coroutines.flow.Flow

class SavedLocationDataSourceImpl private constructor(context: Context): SavedLocationDataSource {
    private val savedLocationsDao: SavedLocationsDao
    init {
        savedLocationsDao = AppDatabase.getInstance(context).weatherDao
    }
    companion object{
        private var instance: SavedLocationDataSourceImpl? = null
        fun getInstance(context: Context): SavedLocationDataSourceImpl{
            return instance ?: run {
                val temp = SavedLocationDataSourceImpl(context)
                instance = temp
                temp
            }
        }
    }
    override suspend fun insert(savedLocations: SavedLocations): Flow<Long> {
        return savedLocationsDao.insert(savedLocations)
    }

    override suspend fun delete(savedLocations: SavedLocations): Flow<Int> {
        return savedLocationsDao.delete(savedLocations)
    }

    override suspend fun deleteAll(): Flow<Int> {
        return savedLocationsDao.deleteAll()
    }

    override suspend fun getAll(): Flow<List<SavedLocations>> {
        return savedLocationsDao.getAll()
    }
}