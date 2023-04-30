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
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.reboot297.sensors.BaseSensorActivity
import com.reboot297.sensors.R

@RequiresApi(Build.VERSION_CODES.O)
class AccelerometerUncalibratedDetailsActivity : BaseSensorActivity(), SensorEventListener {

    override fun startListening() {
        sensorManager.registerListener(
            this@AccelerometerUncalibratedDetailsActivity,
            sensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.values?.let {
            binding.sensorValueView.text = format6Items(it)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        binding.sensorAccuracyView.text = if (accuracy == -1) {
            getString(R.string.accuracy_no_contact)
        } else {
            resources.getStringArray(R.array.accuracy_values)[accuracy]
        }
    }

    override fun getUnit() = R.string.unit_acceleration

    override fun getSensorType() = Sensor.TYPE_ACCELEROMETER_UNCALIBRATED
}