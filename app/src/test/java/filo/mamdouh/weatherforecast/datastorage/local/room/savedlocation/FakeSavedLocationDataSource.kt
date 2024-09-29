package filo.mamdouh.weatherforecast.datastorage.local.room.savedlocation

import filo.mamdouh.weatherforecast.models.LocationItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSavedLocationDataSource : SavedLocationDataSource{
    var savedList = mutableListOf<LocationItem>()
    override fun insert(savedLocations: LocationItem): Flow<Long> {
        savedList.add(savedLocations)
        return flow { emit(1) }
    }

    override fun delete(savedLocations: LocationItem): Flow<Int> {
        savedList.remove(savedLocations)
        return flow { emit(1) }
    }

    override fun deleteAll(): Flow<Int> {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<List<LocationItem>> {
        return flow{ emit(savedList) }
    }

}