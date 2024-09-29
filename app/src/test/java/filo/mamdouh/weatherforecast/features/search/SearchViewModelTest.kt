package filo.mamdouh.weatherforecast.features.search

import androidx.test.ext.junit.runners.AndroidJUnit4
import filo.mamdouh.weatherforecast.features.FakeRepository
import filo.mamdouh.weatherforecast.models.CurrentWeather
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
@Config(manifest=Config.NONE)
class SearchViewModelTest{
    private val viewModel = SearchViewModel(FakeRepository())
    @Test
    fun getDataTest(){
        assert(viewModel.list.value.isEmpty())
    }

    @Test
    fun updateDataTest ()= runBlocking{
        viewModel.getData()
        delay(500)
        var list = emptyList<CurrentWeather>()
        viewModel.list.take(1).collect{
            list = it
        }
        println(list)
        assert(list.isNotEmpty())
    }

    @Test
    fun deleteLocationTest (){
    runBlocking{
        viewModel.getData()
        delay(500)
        viewModel.deleteLocation(0)
        println("HERE")
        delay(500)
        viewModel.list.take(1).collect{
            assert(it.size == 1)
            }
        }
    }
}