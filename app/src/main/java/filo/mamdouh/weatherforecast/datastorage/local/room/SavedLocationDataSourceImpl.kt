package filo.mamdouh.weatherforecast.datastorage.local.room

import android.content.Context
import filo.mamdouh.weatherforecast.models.SavedLocations
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SavedLocationDataSourceImpl @Inject constructor(context: Context): SavedLocationDataSource {
    private val savedLocationsDao: SavedLocationsDao = AppDatabase.getInstance(context).weatherDao

//    companion object{
//        private var instance: SavedLocationDataSourceImpl? = null
//        fun getInstance(context: Context): SavedLocationDataSourceImpl{
//            return instance ?: run {
//                val temp = SavedLocationDataSourceImpl(context)
//                instance = temp
//                temp
//            }
//        }
//    }
    override fun insert(savedLocations: SavedLocations): Flow<Long> {
        return flow{
            emit(savedLocationsDao.insert(savedLocations))
        }
    }

    override fun delete(savedLocations: SavedLocations): Flow<Int> {
        return flow{
            emit(savedLocationsDao.delete(savedLocations))
        }
    }

    override fun deleteAll(): Flow<Int> {
        return flow{
            emit(savedLocationsDao.deleteAll())
        }
    }

    override fun getAll(): Flow<List<SavedLocations>> {
        return savedLocationsDao.getAll()
    }
}