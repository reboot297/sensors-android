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

package com.reboot297.sensors.samples.orientation

import android.os.Build
import android.os.Bundle
import android.view.Display
import android.view.WindowManager
import com.reboot297.sensors.BaseSensorDetailsActivity
import com.reboot297.sensors.databinding.ActivityDeviceOrientationBinding
import com.reboot297.sensors.lib.raw.base.DeviceOrientationValuesListener
import com.reboot297.sensors.lib.samples.DeviceOrientationLifecycleObserver

class DeviceOrientationActivity :
    BaseSensorDetailsActivity(),
    DeviceOrientationValuesListener {
    override val sensorObserver = DeviceOrientationLifecycleObserver(this, this, this)

    private lateinit var binding: ActivityDeviceOrientationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDeviceOrientationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

    override fun getDefaultDisplay(): Display? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            display!!
        } else {
            @Suppress("DEPRECATION")
            (getSystemService(WINDOW_SERVICE) as WindowManager).defaultDisplay
        }

    override fun enableUI(enabled: Boolean) {}

    override fun onValuesChanged(values: FloatArray) {
        binding.orientationDescriptionView.setValues(values)
    }
}
