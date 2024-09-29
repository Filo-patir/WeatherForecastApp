package filo.mamdouh.weatherforecast

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import filo.mamdouh.weatherforecast.datastorage.local.objectbox.ObjectBox
import filo.mamdouh.weatherforecast.logic.NetworkUtils
import filo.mamdouh.weatherforecast.logic.workers.RepeatWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class MainApp : Application(){
    private val _networkState = MutableStateFlow<Boolean>(false)
    val networkState = _networkState.onStart { getNotificationState() }.stateIn(CoroutineScope(
        Dispatchers.IO), SharingStarted.WhileSubscribed(1000L), false)
    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
        val channel = NotificationChannel("alarm_channel", "Alarm Channel", NotificationManager.IMPORTANCE_HIGH)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        scheduleRepeating()
    }
    private fun getNotificationState(){
        CoroutineScope(Dispatchers.IO).launch {
            _networkState.emit(NetworkUtils.isNetworkAvailable(applicationContext))
        }
    }

    private fun scheduleRepeating(){
        val repeat = PeriodicWorkRequestBuilder<RepeatWorker>(3,TimeUnit.DAYS,3,TimeUnit.HOURS).build()
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork("repeat", ExistingPeriodicWorkPolicy.KEEP, repeat)
    }
}