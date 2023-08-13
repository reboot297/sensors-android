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
 * An example of reading raw data from an Android uncalibrated gyroscope sensor.
 *
 * An uncalibrated gyroscope reports the rate of rotation around the sensor axes without applying bias compensation to them, along with a bias estimate.
 * Conceptually, the uncalibrated measurement is the sum of the calibrated measurement and the bias estimate: _uncalibrated = _calibrated + _bias.
 *
 * The x_bias, y_bias and z_bias values are expected to jump as soon as the estimate of the bias changes, and they should be stable the rest of the time.
 * See the definition of the gyroscope sensor for details on the coordinate system used.
 */

class GyroscopeUncalibratedLifecycleObserver(
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
         * getDefaultSensor(SENSOR_TYPE_GYROSCOPE_UNCALIBRATED) returns a non-wake-up sensor
         */
        _sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED)
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
            this@GyroscopeUncalibratedLifecycleObserver,
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
         * Underlying physical sensor: Gyroscope
         *
         * values[0] - Rate of rotation (without drift compensation) around the x axis in rad/s
         * values[1] - Rate of rotation (without drift compensation) around the y axis in rad/s
         * values[2] - Rate of rotation (without drift compensation) around the z axis in rad/s
         * values[3] - Estimated drift around the x axis in rad/s
         * values[4] - Estimated drift around the y axis in rad/s
         * values[5] - Estimated drift around the z axis in rad/s
         */
        valuesListener?.onSensorValuesChanged(event?.values)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        valuesListener?.onAccuracyValueChanged(accuracy)
    }
}
