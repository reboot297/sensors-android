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

package com.reboot297.sensors.wear.samples.altitude

import android.os.Bundle
import com.reboot297.sensors.databinding.ActivitySampleDetailsValuesAltitudeBinding
import com.reboot297.sensors.lib.raw.base.ActivityListener
import com.reboot297.sensors.lib.raw.base.AltitudeValuesListener
import com.reboot297.sensors.lib.samples.AltitudeLifecycleObserver
import com.reboot297.sensors.wear.BaseSensorActivity

class ValuesActivity :
    BaseSensorActivity(),
    ActivityListener,
    AltitudeValuesListener {
    private lateinit var binding: ActivitySampleDetailsValuesAltitudeBinding

    private val sensorObserver =
        AltitudeLifecycleObserver(
            activityListener = this,
            availabilityListener = this,
            valuesListener = this,
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySampleDetailsValuesAltitudeBinding.inflate(layoutInflater)
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

    override fun onValuesChanged(
        pressure: Float,
        altitude: Float,
    ) {
        binding.pressureValueView.setValue(pressure)
        binding.altitudeView.setValue(altitude)
    }
}
