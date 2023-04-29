/*
 * Copyright (c) 2023. Viktor Pop
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.reboot297.sensors

import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.reboot297.sensors.databinding.ActivityRawDataBinding
import com.reboot297.sensors.raw.environment.AmbientTemperatureDetailsActivity
import com.reboot297.sensors.raw.environment.LightDetailsActivity
import com.reboot297.sensors.raw.environment.PressureDetailsActivity
import com.reboot297.sensors.raw.environment.RelativeHumidityDetailsActivity
import com.reboot297.sensors.raw.motion.AccelerometerDetailsActivity
import com.reboot297.sensors.raw.motion.AccelerometerUncalibratedDetailsActivity
import com.reboot297.sensors.raw.motion.GravityDetailsActivity
import com.reboot297.sensors.raw.motion.GyroscopeDetailsActivity
import com.reboot297.sensors.raw.motion.GyroscopeUncalibratedDetailsActivity
import com.reboot297.sensors.raw.motion.LinearAccelerationDetailsActivity
import com.reboot297.sensors.raw.motion.RotationVectorDetailsActivity
import com.reboot297.sensors.raw.motion.SignificantMotionsDetailsActivity
import com.reboot297.sensors.raw.motion.StepCounterDetailsActivity
import com.reboot297.sensors.raw.motion.StepDetectorDetailsActivity
import com.reboot297.sensors.raw.position.ProximityDetailsActivity

class RawDataActivity : BaseActivity() {

    private lateinit var binding: ActivityRawDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRawDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.ambientTemperatureItemView.setOnClickListener {
            startActivity(Intent(this, AmbientTemperatureDetailsActivity::class.java))
        }

        binding.lightItemView.setOnClickListener {
            startActivity(Intent(this, LightDetailsActivity::class.java))
        }

        binding.pressureItemView.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    PressureDetailsActivity::class.java
                )
            )
        }

        binding.relativeHumidityItemView.setOnClickListener {
            startActivity(Intent(this, RelativeHumidityDetailsActivity::class.java))
        }

        binding.proximityItemView.setOnClickListener {
            startActivity(Intent(this, ProximityDetailsActivity::class.java))
        }

        binding.accelerometerItemView.setOnClickListener {
            startActivity(Intent(this, AccelerometerDetailsActivity::class.java))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.accelerometerUncalibratedItemView.isEnabled = true
            binding.accelerometerUncalibratedItemView.setOnClickListener {
                startActivity(Intent(this, AccelerometerUncalibratedDetailsActivity::class.java))
            }
        } else {
            binding.accelerometerUncalibratedItemView.isEnabled = false
            binding.accelerometerUncalibratedItemView.text =
                getString(R.string.raw_data_item_accelerometer_uncalibrated) + "\n" + getString(R.string.warning_api_26)
        }

        binding.gravityItemView.setOnClickListener {
            startActivity(Intent(this, GravityDetailsActivity::class.java))
        }

        binding.gyroscopeItemView.setOnClickListener {
            startActivity(Intent(this, GyroscopeDetailsActivity::class.java))
        }

        binding.gyroscopeUncalibratedItemView.setOnClickListener {
            startActivity(Intent(this, GyroscopeUncalibratedDetailsActivity::class.java))
        }

        binding.linearAccelerationItemView.setOnClickListener {
            startActivity(Intent(this, LinearAccelerationDetailsActivity::class.java))
        }

        binding.rotationVectorItemView.setOnClickListener {
            startActivity(Intent(this, RotationVectorDetailsActivity::class.java))
        }

        binding.significantMotionsItemView.setOnClickListener {
            startActivity(Intent(this, SignificantMotionsDetailsActivity::class.java))
        }

        binding.stepCounterItemView.setOnClickListener {
            startActivity(Intent(this, StepCounterDetailsActivity::class.java))
        }

        binding.stepDetectorItemView.setOnClickListener {
            startActivity(Intent(this, StepDetectorDetailsActivity::class.java))
        }
    }
}