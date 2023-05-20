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

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.hardware.TriggerEvent
import android.hardware.TriggerEventListener
import android.os.Bundle
import com.reboot297.sensors.BaseSensorDetailsActivity
import com.reboot297.sensors.R
import com.reboot297.sensors.sections.SectionUIImpl
import com.reboot297.sensors.sections.accuracy.AccuracySensorValue
import com.reboot297.sensors.sections.description.Description
import com.reboot297.sensors.sections.info.SensorInfo
import com.reboot297.sensors.sections.sensor_values.TriggerSensorValue

class SignificantMotionsDetailsActivity : BaseSensorDetailsActivity() {
    private lateinit var sensorManager: SensorManager
    private var _sensor: Sensor? = null
    private val sensor: Sensor? get() = _sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun createSectionsUI() = SectionUIImpl(
        sensorValue = TriggerSensorValue(R.string.significant_motion_value),
        accuracyValue = AccuracySensorValue(applicationContext),
        sensorInfo = SensorInfo(applicationContext),
        description = Description(R.string.description_significant_motion)
    )

    override fun onStart() {
        super.onStart()
        _sensor = sensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION)
        if (sensor != null) {
            ui.displaySensorInfo(sensor!!, binding.sensorInfoLayout)
        } else {
            showSensorNotAvailableDialog()
        }
    }

    override fun startListening() {
        sensor?.let {
            sensorManager.requestTriggerSensor(triggerEventListener, it)
        }
    }

    override fun stopListening() {
        sensor?.let {
            sensorManager.cancelTriggerSensor(triggerEventListener, sensor)
        }
    }

    private val triggerEventListener = object : TriggerEventListener() {
        override fun onTrigger(event: TriggerEvent?) {
            event?.values?.let { displaySensorValues(it) }
        }
    }
}
