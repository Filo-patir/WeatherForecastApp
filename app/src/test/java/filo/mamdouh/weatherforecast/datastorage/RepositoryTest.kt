package filo.mamdouh.weatherforecast.datastorage

import androidx.test.ext.junit.runners.AndroidJUnit4
import filo.mamdouh.weatherforecast.datastorage.local.objectbox.FakeBoxes
import filo.mamdouh.weatherforecast.datastorage.local.room.alarm.FakeAlarmDataSource
import filo.mamdouh.weatherforecast.datastorage.local.room.savedlocation.FakeSavedLocationDataSource
import filo.mamdouh.weatherforecast.datastorage.local.sharedpref.FakeSharedPreferencesHandler
import filo.mamdouh.weatherforecast.datastorage.network.FakeNetworkDataSource
import filo.mamdouh.weatherforecast.models.LocationItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepositoryTest{
private val repository = Repository(FakeSavedLocationDataSource(), FakeNetworkDataSource(), FakeSharedPreferencesHandler(), FakeAlarmDataSource(),FakeBoxes())
    private val list = listOf(LocationItem(1, "name", 0.0, 0.0, "test", true),
        LocationItem(2, "name2", 0.0, 0.0, "test", false),
        LocationItem(3, "name3", 0.0, 0.0, "test", false))
    @Test
    fun getSavedLocationsTest_GettingEmptyList(){
        runBlocking {
            assert(repository.getSavedLocations().single().isEmpty())
        }
    }

    @Test
    fun getSavedLocationsTest_InsertingList(){
        runBlocking {
            list.forEach { repository.insertSavedLocation(it).single() }
            assert(repository.getSavedLocations().single().size == 3)
        }
    }

    @Test
    fun deleteSavedLocationTest(){
        runBlocking {
            list.forEach { repository.insertSavedLocation(it).single() }
            delay(500)
            assert(repository.getSavedLocations().single().size == 3)
            repository.deleteSavedLocation(list[0]).single()
            println(repository.getSavedLocations().single().size)
            assert(repository.getSavedLocations().single().size == 2)
        }
    }
}