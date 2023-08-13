/*
 * Copyright (c) 2023. Viktor Pop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.reboot297.sensors.wear.samples.deviceorientation

import android.os.Build
import android.os.Bundle
import android.view.Display
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.reboot297.sensors.databinding.ActivitySampleValuesDeviceOrientationBinding
import com.reboot297.sensors.lib.raw.base.ActivityListener
import com.reboot297.sensors.lib.raw.base.DeviceOrientationValuesListener
import com.reboot297.sensors.lib.samples.DeviceOrientationLifecycleObserver
import com.reboot297.sensors.wear.BaseSensorActivity

class ValuesActivity : BaseSensorActivity(), ActivityListener, DeviceOrientationValuesListener {

    private lateinit var binding: ActivitySampleValuesDeviceOrientationBinding

    private val sensorObserver = DeviceOrientationLifecycleObserver(
        activityListener = this,
        availabilityListener = this,
        valuesListener = this,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySampleValuesDeviceOrientationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorObserver.registerLifecycle(lifecycle)

        with(binding) {
            measureSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    sensorObserver.startListening()
                } else {
                    sensorObserver.stopListening()
                }
            }
        }
    }

    override fun onValuesChanged(values: FloatArray) {
        binding.valueView.setValues(values)
    }

    override fun getDefaultDisplay(): Display? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            display!!
        } else {
            @Suppress("DEPRECATION")
            (getSystemService(AppCompatActivity.WINDOW_SERVICE) as WindowManager).defaultDisplay
        }
    }
}