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
import androidx.lifecycle.LifecycleOwner
import com.reboot297.sensors.lib.raw.base.ActivityListener
import com.reboot297.sensors.lib.raw.base.BaseSensorObserver
import com.reboot297.sensors.lib.raw.base.SensorAvailabilityListener
import com.reboot297.sensors.lib.raw.base.SensorValuesListener

/**
 * An example of reading raw data from an Android accelerometer sensor.
 * An accelerometer sensor reports the acceleration of the device along the three sensor axes.
 * The measured acceleration includes both the physical acceleration (change of velocity) and the gravity
 * - The norm of (x, y, z) should be close to 0 when in free fall.
 * - When the device lies flat on a table and is pushed on its left side toward the right, the x acceleration value is positive.
 * - When the device lies flat on a table, the acceleration value along z is +9.81 alo,
 *      which corresponds to the acceleration of the device (0 m/s^2) minus the force of gravity (-9.81 m/s^2).
 * - When the device lies flat on a table and is pushed toward the sky, the acceleration value is greater than +9.81,
 *      which corresponds to the acceleration of the device (+A m/s^2) minus the force of gravity (-9.81 m/s^2).
 *
 * If you need to get acceleration without gravity
 * @see LinearAccelerationLifecycleObserver sample
 */

class AccelerometerLifecycleObserver(
    private val activityListener: ActivityListener,
    private val availabilityListener: SensorAvailabilityListener? = null,
    private val valuesListener: SensorValuesListener? = null,
) : BaseSensorObserver(),
    SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        with(activityListener.getApplicationContext()) {
            sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        /**
         * getDefaultSensor(SENSOR_TYPE_ACCELEROMETER) returns a non-wake-up sensor
         */
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
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
            this@AccelerometerLifecycleObserver,
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
         * values[0] - Acceleration force along the x axis (including gravity) in m/s^2
         * values[1] - Acceleration force along the y axis (including gravity) in m/s^2
         * values[2] - Acceleration force along the z axis (including gravity) in m/s^2
         */
        valuesListener?.onSensorValuesChanged(event?.values)
    }

    override fun onAccuracyChanged(
        sensor: Sensor?,
        accuracy: Int,
    ) {
        valuesListener?.onAccuracyValueChanged(accuracy)
    }
}
