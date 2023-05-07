package com.reboot297.sensors

import android.content.Intent
import android.os.Bundle
import androidx.core.view.WindowCompat
import com.reboot297.sensors.altitude.AltitudeActivity
import com.reboot297.sensors.databinding.ActivityHomeBinding
import com.reboot297.sensors.orientation.DeviceOrientationActivity

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.rawDataButton.setOnClickListener {
            startActivity(Intent(this, RawDataActivity::class.java))
        }

        binding.altitudeButton.setOnClickListener {
            startActivity(Intent(this, AltitudeActivity::class.java))
        }

        binding.deviceOrientationButton.setOnClickListener {
            startActivity(Intent(this, DeviceOrientationActivity::class.java))
        }
    }
}