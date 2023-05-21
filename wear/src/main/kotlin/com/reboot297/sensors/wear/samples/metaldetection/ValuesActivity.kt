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

package com.reboot297.sensors.wear.samples.metaldetection

import android.os.Bundle
import com.reboot297.sensors.databinding.ActivitySampleValuesMetalDetectionBinding
import com.reboot297.sensors.lib.raw.base.ActivityListener
import com.reboot297.sensors.lib.raw.base.MetalDetectionValuesListener
import com.reboot297.sensors.lib.samples.MetalDetectionLifecycleObserver
import com.reboot297.sensors.wear.BaseSensorActivity

class ValuesActivity : BaseSensorActivity(), ActivityListener, MetalDetectionValuesListener {

    private lateinit var binding: ActivitySampleValuesMetalDetectionBinding

    private val sensorObserver = MetalDetectionLifecycleObserver(
        activityListener = this,
        availabilityListener = this,
        valuesListener = this,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySampleValuesMetalDetectionBinding.inflate(layoutInflater)
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

    override fun onValuesChanged(rawValues: FloatArray?, value: Double) {
        binding.sensorValuesView.setValues(rawValues)
        binding.valueView.setValue(value)
    }
}
