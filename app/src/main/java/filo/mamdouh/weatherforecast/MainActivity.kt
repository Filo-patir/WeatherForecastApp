package filo.mamdouh.weatherforecast

import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import filo.mamdouh.weatherforecast.features.alarm.AlarmSchedulerImpl
import filo.mamdouh.weatherforecast.models.AlarmItem
import java.time.LocalDateTime


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Filo", "onCreate: ${BuildConfig.API_KEY}")
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val alarmScheduler = AlarmSchedulerImpl(this)
        alarmScheduler.scheduleAlarm(AlarmItem(LocalDateTime.now().plusSeconds(10), "Test",
            flag = true,
            result = true
        ))
    }
    private fun checkPermissions() : Boolean {
        val finePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val coarsePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val notificationPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        val overlayPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.SYSTEM_ALERT_WINDOW) == PackageManager.PERMISSION_GRANTED
//        val intent = Intent(
//            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//            Uri.parse("package:$packageName")
//        )
//        startActivity(intent, null)
        return finePermission || coarsePermission && notificationPermission && overlayPermission
    }

    override fun onStart() {
        super.onStart()
        if(checkPermissions()){
            if(LocationManagerCompat.isLocationEnabled(getSystemService(LOCATION_SERVICE) as LocationManager))
                Log.d("Filo", "onStart: Location Enabled")
            else {
                Log.d("Filo", "onStart: Location Disabled")
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.POST_NOTIFICATIONS,
                        android.Manifest.permission.SYSTEM_ALERT_WINDOW
                    ),
                    1
                )
            }
        } else {
            ActivityCompat.requestPermissions(this,  arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION , android.Manifest.permission.POST_NOTIFICATIONS, android.Manifest.permission.SYSTEM_ALERT_WINDOW), 1)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Filo", "onRequestPermissionsResult: Permission Granted")
            } else {
                Log.d("Filo", "onRequestPermissionsResult: Permission Denied")
            }
        }
    }
}