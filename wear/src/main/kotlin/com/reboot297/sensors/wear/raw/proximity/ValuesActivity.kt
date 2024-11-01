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

package com.reboot297.sensors.wear.raw.proximity

import android.os.Bundle
import com.reboot297.sensors.databinding.ActivityRawDetailsValuesProximityBinding
import com.reboot297.sensors.lib.raw.ProximityLifecycleObserver
import com.reboot297.sensors.lib.raw.base.ActivityListener
import com.reboot297.sensors.lib.raw.base.SensorValuesListener
import com.reboot297.sensors.wear.BaseSensorActivity

class ValuesActivity :
    BaseSensorActivity(),
    SensorValuesListener,
    ActivityListener {
    private lateinit var binding: ActivityRawDetailsValuesProximityBinding

    private val sensorObserver =
        ProximityLifecycleObserver(
            activityListener = this,
            availabilityListener = this,
            valuesListener = this,
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRawDetailsValuesProximityBinding.inflate(layoutInflater)
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

    override fun onSensorValuesChanged(values: FloatArray?) {
        values?.let {
            binding.valuesView.setValues(values)
        }
    }

    override fun onAccuracyValueChanged(accuracy: Int) {
        binding.accuracyView.setValue(accuracy)
    }
}
