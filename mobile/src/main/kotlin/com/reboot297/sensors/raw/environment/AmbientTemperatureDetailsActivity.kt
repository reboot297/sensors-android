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

package com.reboot297.sensors.raw.environment

import android.hardware.Sensor
import android.os.Bundle
import com.reboot297.sensors.BaseSensorValuesDetailsActivity
import com.reboot297.sensors.R
import com.reboot297.sensors.databinding.ActivityRawAmbientTemperatureBinding
import com.reboot297.sensors.extensions.handleVisibility
import com.reboot297.sensors.lib.raw.AmbientTemperatureLifecycleObserver

class AmbientTemperatureDetailsActivity : BaseSensorValuesDetailsActivity() {
    override val sensorObserver = AmbientTemperatureLifecycleObserver(this, this, this)

    lateinit var binding: ActivityRawAmbientTemperatureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRawAmbientTemperatureBinding.inflate(layoutInflater)
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

            sensorDataLabelView.handleVisibility(
                sensorValueLabelView,
                sensorValueView,
                sensorAccuracyLabelView,
                sensorAccuracyView,
            )
            sensorInfoLabelView.handleVisibility(sensorInfoLayout)
            sensorDescriptionLabelView.handleVisibility(sensorDescriptionView)
        }
    }

    override fun onSensorValuesChanged(values: FloatArray?) {
        binding.sensorValueView.setValues(values)
    }

    override fun onAccuracyValueChanged(accuracy: Int) {
        binding.sensorAccuracyView.setValue(accuracy)
    }

    override fun onSensorAvailable(sensor: Sensor) {
        super.onSensorAvailable(sensor)
        binding.sensorInfoLayout.setInfo(sensor, getString(R.string.unit_temperature))
    }

    override fun enableUI(enabled: Boolean) {
        with(binding) {
            measureSwitch.isEnabled = enabled
            sensorInfoLabelView.isEnabled = enabled
            sensorDataLabelView.isEnabled = enabled
        }
    }
}
