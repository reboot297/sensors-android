/*
 * Copyright (c) 2023. Viktor Pop
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.reboot297.sensors

import android.content.Intent
import android.hardware.Sensor
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.reboot297.sensors.lib.raw.base.ActivityListener
import com.reboot297.sensors.lib.raw.base.BaseSensorObserver
import com.reboot297.sensors.lib.raw.base.SensorAvailabilityListener

abstract class BaseSensorDetailsActivity :
    BaseActivity(),
    SensorAvailabilityListener,
    ActivityListener {
    protected abstract val sensorObserver: BaseSensorObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorObserver.registerLifecycle(lifecycle)
    }

    private fun showSensorNotAvailableDialog(): AlertDialog =
        AlertDialog
            .Builder(this)
            .setMessage(R.string.warning_sensor_not_available)
            .setPositiveButton(android.R.string.ok) { _, _ -> finish() }
            .setCancelable(false)
            .show()

    override fun onSensorAvailable(sensor: Sensor) {
        enableUI(true)
    }

    override fun onSensorNotAvailable() {
        enableUI(false)
        showSensorNotAvailableDialog()
    }

    abstract fun enableUI(enabled: Boolean)

    protected fun openSample(sampleActivityClass: Class<*>) {
        startActivity(
            Intent(this@BaseSensorDetailsActivity, sampleActivityClass),
        )
    }
}
