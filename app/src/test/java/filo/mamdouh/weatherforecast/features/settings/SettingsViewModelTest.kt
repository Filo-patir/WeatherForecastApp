package filo.mamdouh.weatherforecast.features.settings

import androidx.test.ext.junit.runners.AndroidJUnit4
import filo.mamdouh.weatherforecast.features.FakeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsViewModelTest {
    private val viewModel = SettingsViewModel(FakeRepository())
    @Test
    fun getLocalization() {
        runBlocking {
            viewModel.getLocalization()
            delay(500)
            assert(viewModel.local.value)
        }
    }

    @Test
    fun setLocalization(){
        runBlocking {
            viewModel.setLocalization("ar")
            viewModel.getLocalization()
            delay(500)
            assert(!viewModel.local.value)
        }
    }
}