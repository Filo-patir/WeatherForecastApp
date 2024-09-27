package filo.mamdouh.weatherforecast.features.alarm

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.view.WindowManager
import androidx.annotation.RequiresApi
import filo.mamdouh.weatherforecast.R
import filo.mamdouh.weatherforecast.databinding.OverlayLayoutBinding
import filo.mamdouh.weatherforecast.models.AlarmItem
import java.time.LocalDateTime


class OverlayServices : Service() {
    private lateinit var windowManager: WindowManager
    private lateinit var overlayView: View
    lateinit var mediaPlayer: MediaPlayer
    lateinit var alarmItem: AlarmItem

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        alarmItem = intent?.getParcelableExtra("alarmItem", AlarmItem::class.java) ?: AlarmItem(
            LocalDateTime.now(), "Alarm", false,false,0)
        val binding = OverlayLayoutBinding.inflate(LayoutInflater.from(this))
        overlayView = binding.root
        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        layoutParams.gravity = Gravity.TOP
        val margin = MarginLayoutParams(layoutParams)
        margin.setMargins(20, 50, 50, 20)
        overlayView.layoutParams = margin
        binding.msgTxt.text = alarmItem.message
        windowManager.addView(overlayView, layoutParams)
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm_sound)
        mediaPlayer.isLooping = true
        mediaPlayer.start()
        binding.close.setOnClickListener {
            mediaPlayer.stop()
            stopSelf()
        }

        overlayView.setOnTouchListener { v, event -> // Close overlay on touch outside
            stopSelf()
            true
        }
        return super.onStartCommand(intent, flags, startId)
    }
    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
    }

    override fun onDestroy() {
        super.onDestroy()
        windowManager.removeView(overlayView)
        mediaPlayer.stop()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
