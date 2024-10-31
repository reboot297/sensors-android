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
 * An example of reading raw data from an Android linear acceleration sensor.
 *
 * A linear acceleration sensor reports the linear acceleration of the device in the sensor frame, not including gravity.
 * The output is conceptually: output of the accelerometer minus the output of the gravity sensor.
 * Readings on all axes should be close to 0 when the device is immobile.
 *
 *  The output of the accelerometer, gravity and  linear-acceleration sensors must obey the
 *  following relation:
 *      acceleration = gravity + linear-acceleration
 */

class LinearAccelerationLifecycleObserver(
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
         * getDefaultSensor(SENSOR_TYPE_LINEAR_ACCELERATION) returns a non-wake-up sensor
         */
        _sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
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
            this@LinearAccelerationLifecycleObserver,
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
         * Underlying physical sensors: Accelerometer and (if present) gyroscope (or magnetometer if gyroscope not present)
         *
         * values[0] - Acceleration force along the x axis (excluding gravity) in m/s^2
         * values[1] - Acceleration force along the y axis (excluding gravity) in m/s^2
         * values[2] - Acceleration force along the z axis (excluding gravity) in m/s^2
         */
        valuesListener?.onSensorValuesChanged(event?.values)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        valuesListener?.onAccuracyValueChanged(accuracy)
    }
}
