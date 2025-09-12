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

package com.reboot297.sensors.wear

import android.hardware.Sensor
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.reboot297.sensors.R
import com.reboot297.sensors.lib.raw.base.SensorAvailabilityListener

abstract class BaseSensorActivity :
    ComponentActivity(),
    SensorAvailabilityListener {
    override fun onSensorAvailable(sensor: Sensor) {}

    override fun onSensorNotAvailable() {
        Toast.makeText(this, R.string.warning_sensor_not_available, Toast.LENGTH_LONG).show()
        finish()
    }
}
