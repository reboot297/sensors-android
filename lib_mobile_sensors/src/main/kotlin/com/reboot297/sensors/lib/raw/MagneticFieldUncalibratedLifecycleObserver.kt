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
 * An example of reading raw data from an Android magnetic field sensor.
 *
 * Similar to Sensor.TYPE_MAGNETIC_FIELD, but the hard iron calibration is reported separately instead of being included in the measurement.
 * Factory calibration and temperature compensation will still be applied to the "uncalibrated" measurement.
 * Assumptions that the magnetic field is due to the Earth's poles is avoided.
 *
 * x_uncalib, y_uncalib, z_uncalib are the measured magnetic field in X, Y, Z axes.
 * Soft iron and temperature calibrations are applied. But the hard iron calibration is not applied. The values are in micro-Tesla (uT).
 *
 * x_bias, y_bias, z_bias give the iron bias estimated in X, Y, Z axes. Each field is a component of the estimated hard iron calibration.
 * The values are in micro-Tesla (uT).
 *
 * Hard iron - These distortions arise due to the magnetized iron, steel or permanent magnets on the device.
 * Soft iron - These distortions arise due to the interaction with the earth's magnetic field.
 *
 * Conceptually, the uncalibrated measurement is the sum of the calibrated measurement and the bias estimate: _uncalibrated = _calibrated + _bias.
 */
class MagneticFieldUncalibratedLifecycleObserver(
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
         * getDefaultSensor(SENSOR_TYPE_MAGNETIC_FIELD_UNCALIBRATED) returns a non-wake-up sensor
         */
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED)
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
            this@MagneticFieldUncalibratedLifecycleObserver,
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
         * Underlying physical sensor: Magnetometer
         *
         * values[0] = magnetic field (without hard-iron compensation) along the X axis
         * values[1] = magnetic field (without hard-iron compensation) along the Y axis
         * values[2] = magnetic field (without hard-iron compensation) along the Z axis
         * values[3] = estimated hard-iron bias along the X axis
         * values[4] = estimated hard-iron bias along the Y axis
         * values[5] = estimated hard-iron bias along the X axis
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
