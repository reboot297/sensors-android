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

package com.reboot297.sensors.raw.motion

import android.hardware.Sensor
import android.hardware.TriggerEvent
import android.os.Bundle
import com.reboot297.sensors.BaseSensorTriggerDetailsActivity
import com.reboot297.sensors.R
import com.reboot297.sensors.databinding.ActivityRawSignificatMotionBinding
import com.reboot297.sensors.extensions.handleVisibility
import com.reboot297.sensors.lib.raw.SignificantMotionLifecycleObserver

class SignificantMotionsDetailsActivity : BaseSensorTriggerDetailsActivity() {
    override val sensorObserver = SignificantMotionLifecycleObserver(this, this, this)

    lateinit var binding: ActivityRawSignificatMotionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRawSignificatMotionBinding.inflate(layoutInflater)
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
            )

            sensorInfoLabelView.handleVisibility(sensorInfoLayout)
            sensorDescriptionLabelView.handleVisibility(sensorDescriptionView)
        }
    }

    override fun onSensorAvailable(sensor: Sensor) {
        super.onSensorAvailable(sensor)
        binding.sensorInfoLayout.setInfo(
            sensor,
            getString(R.string.unitless),
        )
    }

    override fun onTrigger(event: TriggerEvent) {
        binding.sensorValueView.setValues(event)
    }

    override fun enableUI(enabled: Boolean) {
        with(binding) {
            measureSwitch.isEnabled = enabled
            sensorInfoLabelView.isEnabled = enabled
            sensorDataLabelView.isEnabled = enabled
        }
    }
}
