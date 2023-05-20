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

package com.reboot297.sensors.raw.position

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import com.reboot297.sensors.BaseSensorDetailsActivity
import com.reboot297.sensors.R
import com.reboot297.sensors.sections.SectionUIImpl
import com.reboot297.sensors.sections.accuracy.AccuracySensorValue
import com.reboot297.sensors.sections.description.Description
import com.reboot297.sensors.sections.info.SensorInfo
import com.reboot297.sensors.sections.sensor_values.SixSensorValues

class MagneticFieldUncalibratedDetailsActivity : BaseSensorDetailsActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var _sensor: Sensor? = null
    private val sensor: Sensor? get() = _sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun createSectionsUI() = SectionUIImpl(
        sensorValue = SixSensorValues(unit = getString(R.string.unit_magnetic_field)),
        accuracyValue = AccuracySensorValue(applicationContext),
        sensorInfo = SensorInfo(applicationContext, getString(R.string.unit_magnetic_field)),
        description = Description(R.string.description_magnetic_field_uncalibrated)
    )

    override fun onStart() {
        super.onStart()
        _sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED)
        if (sensor != null) {
            ui.displaySensorInfo(sensor!!, binding.sensorInfoLayout)
        } else {
            showSensorNotAvailableDialog()
        }
    }

    override fun startListening() {
        sensorManager.registerListener(
            this@MagneticFieldUncalibratedDetailsActivity,
            sensor,
            SensorManager.SENSOR_DELAY_NORMAL,
        )
    }

    override fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.values?.let { displaySensorValues(it) }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        ui.displaySensorAccuracy(binding.sensorDataLayout.sensorAccuracyView, accuracy)
    }
}
