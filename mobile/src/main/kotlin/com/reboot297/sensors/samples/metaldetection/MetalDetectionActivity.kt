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

package com.reboot297.sensors.samples.metaldetection

import android.os.Bundle
import com.reboot297.sensors.BaseSensorDetailsActivity
import com.reboot297.sensors.databinding.ActivityMetalDetectionBinding
import com.reboot297.sensors.lib.raw.base.MetalDetectionValuesListener
import com.reboot297.sensors.lib.samples.MetalDetectionLifecycleObserver

class MetalDetectionActivity :
    BaseSensorDetailsActivity(),
    MetalDetectionValuesListener {
    override val sensorObserver = MetalDetectionLifecycleObserver(this, this, this)
    private lateinit var binding: ActivityMetalDetectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMetalDetectionBinding.inflate(layoutInflater)
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

    override fun enableUI(enabled: Boolean) {}

    override fun onValuesChanged(
        rawValues: FloatArray?,
        value: Double,
    ) {
        binding.sensorValuesView.setValues(rawValues)
        binding.valueView.setValue(value)
    }
}
