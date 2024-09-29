package filo.mamdouh.weatherforecast

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import filo.mamdouh.weatherforecast.contracts.SettingsUpdater
import filo.mamdouh.weatherforecast.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.util.Locale


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SettingsUpdater {
    private val viewModel by viewModels<MainViewModel>()
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(isDay())
            binding.root.setBackgroundResource(R.drawable.sunny_background_color)
        else
            binding.root.setBackgroundResource(R.drawable.night_background_color)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        lifecycleScope.launch {
            checkAndChangLocality(viewModel.getLocalization())
        }
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
        return (finePermission || coarsePermission) && notificationPermission && overlayPermission
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
    override fun checkAndChangLocality(languageCode: String)
    {
        val locale = resources.configuration.locales[0]
        Log.d("Filo", "checkAndChangLocality: ${locale.language != languageCode}")
        if(locale.language != languageCode)
        {
            val newLocale = Locale(languageCode)
            Locale.setDefault(newLocale)
            val config = resources.configuration
            config.setLocale(newLocale)
            config.setLayoutDirection(newLocale)
            resources.updateConfiguration(config,resources.displayMetrics)
            recreate()
        }
    }

    fun isDay(): Boolean {
        val currentTime = LocalTime.now()
        val dayStart = LocalTime.of(6, 0)  // 6:00 AM
        val nightStart = LocalTime.of(18, 0)  // 6:00 PM

        return when {
            currentTime.isAfter(dayStart) && currentTime.isBefore(nightStart) -> {
                true
            }

            else -> {
                false
            }
        }
    }

}