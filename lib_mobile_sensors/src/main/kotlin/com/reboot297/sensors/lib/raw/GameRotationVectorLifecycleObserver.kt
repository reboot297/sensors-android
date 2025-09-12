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
 * An example of reading raw data from an Android rotation vector sensor.
 *
 * The game rotation vector sensor is identical to the Rotation vector sensor, except it does not use
 * the geomagnetic field. Therefore the Y axis does not point north but instead to some other reference.
 * That reference is allowed to drift by the same order of magnitude as the gyroscope drifts around the Z axis.
 *
 * Because the game rotation vector sensor does not use the magnetic field, relative rotations
 * are more accurate, and not impacted by magnetic field changes. Use this sensor in a game if you
 * do not care about where north is, and the normal rotation vector does not fit your needs because
 * of its reliance on the magnetic field.
 */
class GameRotationVectorLifecycleObserver(
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
         * getDefaultSensor(SENSOR_TYPE_GAME_ROTATION_VECTOR) returns a non-wake-up sensor
         */
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR)
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
            this@GameRotationVectorLifecycleObserver,
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
         * Underlying physical sensors: Accelerometer and gyroscope (no magnetometer)
         *
         * values[0]: Rotation vector component along the x axis (x * sin(θ/2)).
         * values[1]: Rotation vector component along the y axis (y * sin(θ/2)).
         * values[2]: Rotation vector component along the z axis (z * sin(θ/2)).
         * values[3]: Scalar component of the rotation vector ((cos(θ/2)).
         *              (before API 18 - optional value)
         *
         * This sensor doesn't report an estimated heading accuracy
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
