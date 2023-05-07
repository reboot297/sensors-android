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

import android.Manifest
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.reboot297.sensors.BaseSensorActivity
import com.reboot297.sensors.R
import com.reboot297.sensors.databinding.ActivityDetailsBinding
import java.util.Date

class StepDetectorDetailsActivity : BaseSensorActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var _sensor: Sensor? = null
    private val sensor: Sensor? get() = _sensor
    private lateinit var binding: ActivityDetailsBinding

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            initSensor()
        } else {
            Snackbar.make(binding.root, R.string.permission_denied, Snackbar.LENGTH_LONG)
                .setAction(R.string.settings) {
                    startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", packageName, null)
                    })
                }
                .show()
        }
        enableUI(isGranted)
    }

    private fun enableUI(enabled: Boolean) {
        with(binding) {
            measureSwitch.isEnabled = enabled
            sensorInfoLabelView.isEnabled = enabled
            sensorInfoLayout.root.isVisible = enabled
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        with(binding) {
            measureSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
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

            sensorDescriptionView.setText(R.string.description_step_detector)
        }
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            requestPermissionLauncher.launch(Manifest.permission.ACTIVITY_RECOGNITION)
        } else {
            initSensor()
        }
    }

    private fun initSensor() {
        _sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
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
            this@StepDetectorDetailsActivity,
            sensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    private fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.values?.firstOrNull()?.let {
            binding.sensorDataLayout.sensorValueView.text = getString(R.string.step_detection_value, Date())
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        binding.sensorDataLayout.sensorAccuracyView.text = if (accuracy == -1) {
            getString(R.string.accuracy_no_contact)
        } else {
            resources.getStringArray(R.array.accuracy_values)[accuracy]
        }
    }

    override fun getUnitResId() = R.string.unitless
}