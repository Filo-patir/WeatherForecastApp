package filo.mamdouh.weatherforecast.logic.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import filo.mamdouh.weatherforecast.datastorage.Repository
import filo.mamdouh.weatherforecast.datastorage.local.objectbox.Boxes
import filo.mamdouh.weatherforecast.datastorage.local.room.alarm.AlarmDataSourceImpl
import filo.mamdouh.weatherforecast.datastorage.local.room.savedlocation.SavedLocationDataSourceImpl
import filo.mamdouh.weatherforecast.datastorage.local.sharedpref.SharedPreferencesHandler
import filo.mamdouh.weatherforecast.datastorage.network.NetworkDataSourceImpl
import filo.mamdouh.weatherforecast.models.CachedCompositeKey
import filo.mamdouh.weatherforecast.models.CachedData
import filo.mamdouh.weatherforecast.models.Sys
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltWorker
class FetchWorker (context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    private val repository = Repository(
        SavedLocationDataSourceImpl(context),
        NetworkDataSourceImpl(),
        SharedPreferencesHandler(context),
        AlarmDataSourceImpl(context),
        Boxes()
    )
    private val TAG = "worker_count"
    private var count : Int = try{
        var c = 0
        CoroutineScope(Dispatchers.IO).launch {
            repository.getSettings(TAG).collect {
                if (it.isNotBlank())
                    c = it.toInt()
            }
        }
        c
    } catch (e: Exception) {
        Log.d("Filo", "workmanager: $e")
        0
    }
    override fun doWork(): Result {
        if (count>= 3){
            Log.d("Filo", "doWork: Cleared")
            WorkManager.getInstance(applicationContext).cancelWorkById(id)
            repository.saveSettings(TAG, "0")
            Log.d("Filo", "doWork: Cleared")
        }
            try {
                fetchData()
                return Result.success()
            } catch (e: Exception) {
                return Result.failure()
            }
    }

    private fun fetchData(){
        CoroutineScope(Dispatchers.IO).launch{
            repository.getSavedLocations().map { location -> location.filter { it.home } }.collect { list ->
                list.forEach { item ->
                    val value = repository.getWeeklyForecast(item.lat,item.lon,"metric")
                    if (value.isSuccessful)
                    {
                        val data = value.body()
                        Log.d("Filo", "doWork: $count")
                        data?.list?.forEach{
                            repository.saveWeeklyForecast(
                                CachedData(
                                    0,
                                    CachedCompositeKey(it.dt,data.city),
                                    it.clouds,
                                    it.main,
                                    Sys(sunset = data.city.sunset.toLong() , sunrise = data.city.sunrise.toLong()),
                                    data.city.timezone,
                                    it.visibility,
                                    it.weather,
                                    it.wind), Dispatchers.IO)
                        }
                        repository.saveSettings(TAG, (++count).toString())
                    }
                }
            }
        }
    }
}