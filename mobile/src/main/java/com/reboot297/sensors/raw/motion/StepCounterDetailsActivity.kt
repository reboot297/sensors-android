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

import android.Manifest.permission.ACTIVITY_RECOGNITION
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
import com.reboot297.sensors.BaseSensorDetailsActivity
import com.reboot297.sensors.R
import com.reboot297.sensors.sections.description.Description
import com.reboot297.sensors.sections.sensor_values.OneSensorValue
import com.reboot297.sensors.sections.SectionUIImpl
import com.reboot297.sensors.sections.accuracy.AccuracySensorValue

class StepCounterDetailsActivity : BaseSensorDetailsActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var _sensor: Sensor? = null
    private val sensor: Sensor? get() = _sensor

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            initSensor()
        } else {
            Snackbar.make(binding.root, R.string.permission_denied, Snackbar.LENGTH_LONG)
                .setAction(R.string.settings) {
                    startActivity(
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", packageName, null)
                        },
                    )
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
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun createSectionsUI() = SectionUIImpl(
        sensorValue = OneSensorValue(unit = getString(R.string.unit_step)),
        accuracyValue = AccuracySensorValue(this),
        description = Description(R.string.description_step_counter)
    )

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            requestPermissionLauncher.launch(ACTIVITY_RECOGNITION)
        } else {
            initSensor()
        }
    }

    private fun initSensor() {
        _sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (sensor != null) {
            displaySensorInfo(sensor!!, binding.sensorInfoLayout)
        } else {
            showSensorNotAvailableDialog()
        }
    }

    override fun startListening() {
        with(binding) {
            sensorDataLayout.root.isVisible = true
        }

        sensorManager.registerListener(
            this@StepCounterDetailsActivity,
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

    override fun getUnitResId() = R.string.unit_step
}
