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
import androidx.core.view.isVisible
import com.reboot297.sensors.BaseSensorActivity
import com.reboot297.sensors.R
import com.reboot297.sensors.databinding.ActivityDetailsBinding

class MagneticFieldUncalibratedDetailsActivity : BaseSensorActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var _sensor: Sensor? = null
    private val sensor: Sensor? get() = _sensor
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        with(binding) {
            measureSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    sensorDataLayout.root.isVisible = true
                    startListening()
                } else {
                    stopListening()
                }
            }

            sensorDataLabelView.setOnClickListener {
                sensorDataLayout.root.isVisible = sensor != null && !sensorDataLayout.root.isVisible
            }

            sensorInfoLabelView.setOnClickListener {
                sensorInfoLayout.root.isVisible = sensor != null && !sensorInfoLayout.root.isVisible
            }

            sensorDescriptionLabelView.setOnClickListener {
                sensorDescriptionView.isVisible = !sensorDescriptionView.isVisible
            }

            sensorDescriptionView.setText(R.string.description_magnetic_field_uncalibrated)
        }
    }

    override fun onStart() {
        super.onStart()
        _sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED)
        if (sensor != null) {
            displaySensorInfo(sensor!!, binding.sensorInfoLayout)
        } else {
            showSensorNotAvailableDialog()
        }
    }

    override fun onStop() {
        super.onStop()
        stopListening()
    }

    private fun startListening() {
        sensorManager.registerListener(
            this@MagneticFieldUncalibratedDetailsActivity,
            sensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    private fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.values?.let {
            binding.sensorDataLayout.sensorValueView.text = format6Items(it)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        binding.sensorDataLayout.sensorAccuracyView.text = if (accuracy == -1) {
            getString(R.string.accuracy_no_contact)
        } else {
            resources.getStringArray(R.array.accuracy_values)[accuracy]
        }
    }

    override fun getUnitResId() = R.string.unit_magnetic_field
}