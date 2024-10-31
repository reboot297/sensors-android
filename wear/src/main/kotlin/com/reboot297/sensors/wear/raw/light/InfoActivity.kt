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

package com.reboot297.sensors.wear.raw.light

import android.hardware.Sensor
import android.os.Bundle
import com.reboot297.sensors.R
import com.reboot297.sensors.databinding.ActivityRawDetailsSensorInfoBinding
import com.reboot297.sensors.lib.raw.LightLifecycleObserver
import com.reboot297.sensors.lib.raw.base.ActivityListener
import com.reboot297.sensors.wear.BaseSensorActivity

class InfoActivity : BaseSensorActivity(), ActivityListener {

    private lateinit var binding: ActivityRawDetailsSensorInfoBinding

    private val sensorObserver = LightLifecycleObserver(
        activityListener = this,
        availabilityListener = this,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRawDetailsSensorInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sensorObserver.registerLifecycle(lifecycle)
    }

    override fun onSensorAvailable(sensor: Sensor) {
        binding.sensorInfoLayout.setInfo(sensor, getString(R.string.unit_light))
    }
}
