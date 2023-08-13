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

package com.reboot297.sensors.lib.raw

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import com.reboot297.sensors.lib.raw.base.ActivityListener
import com.reboot297.sensors.lib.raw.base.BaseSensorObserver
import com.reboot297.sensors.lib.raw.base.SensorAvailabilityListener
import com.reboot297.sensors.lib.raw.base.SensorValuesListener

/**
 * An example of reading raw data from an Android uncalibrated accelerometer sensor.
 * This sensor is available since API version 26.
 * Underlying physical sensor: Accelerometer
 *
 * An uncalibrated accelerometer sensor reports the acceleration of the device along
 * the three sensor axes without any bias correction
 * (factory bias and temperature compensation are applied to uncalibrated measurements), along with a bias estimate.
 */
@RequiresApi(Build.VERSION_CODES.O)
class AccelerometerUncalibratedLifecycleObserver(
    private val activityListener: ActivityListener,
    private val availabilityListener: SensorAvailabilityListener? = null,
    private val valuesListener: SensorValuesListener? = null,
) : BaseSensorObserver(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var _sensor: Sensor? = null
    private val sensor: Sensor? get() = _sensor

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        with(activityListener.getApplicationContext()) {
            sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        /**
         * getDefaultSensor(SENSOR_TYPE_ACCELEROMETER_UNCALIBRATED) returns a non-wake-up sensor
         */
        _sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED)
        availabilityListener?.let {
            if (sensor != null) {
                it.onSensorAvailable(sensor!!)
            } else {
                it.onSensorNotAvailable()
            }
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        stopListening()
    }

    override fun startListening() {
        sensorManager.registerListener(
            this@AccelerometerUncalibratedLifecycleObserver,
            sensor,
            SensorManager.SENSOR_DELAY_NORMAL,
        )
    }

    override fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        /**
         * Reporting-mode: Continuous
         * Underlying physical sensor: Accelerometer
         *
         * values[0] - acceleration (without bias compensation) along the X axis in m/s^2
         * values[1] - acceleration (without bias compensation) along the Y axis in m/s^2
         * values[2] - acceleration (without bias compensation) along the Z axis in m/s^2
         * values[3] - estimated bias along X axis in m/s^2
         * values[4] - estimated bias along Y axis in m/s^2
         * values[5] - estimated bias along Z axis in m/s^2
         */
        valuesListener?.onSensorValuesChanged(event?.values)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        valuesListener?.onAccuracyValueChanged(accuracy)
    }
}
