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

import android.content.Intent
import android.hardware.Sensor
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.reboot297.sensors.BaseSensorValuesDetailsActivity
import com.reboot297.sensors.R
import com.reboot297.sensors.databinding.ActivityRawStepCounterBinding
import com.reboot297.sensors.extensions.handleVisibility
import com.reboot297.sensors.lib.raw.StepCounterLifecycleObserver

class StepCounterDetailsActivity : BaseSensorValuesDetailsActivity() {

    override val sensorObserver = StepCounterLifecycleObserver(this, this, this)

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            sensorObserver.permissionGranted()
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
    }

    override fun requestPermissions(permission: String) {
        requestPermissionLauncher.launch(permission)
    }

    lateinit var binding: ActivityRawStepCounterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRawStepCounterBinding.inflate(layoutInflater)
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
        binding.sensorInfoLayout.setInfo(sensor, getString(R.string.unit_step))
    }

    override fun enableUI(enabled: Boolean) {
        with(binding) {
            measureSwitch.isEnabled = enabled
            sensorInfoLabelView.isEnabled = enabled
            sensorDataLabelView.isEnabled = enabled
        }
    }
}
