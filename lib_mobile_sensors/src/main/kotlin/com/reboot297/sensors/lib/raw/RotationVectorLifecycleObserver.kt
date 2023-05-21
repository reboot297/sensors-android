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
 * A rotation vector sensor reports the orientation of the device relative to the East-North-Up coordinates frame.
 * It's usually obtained by integration of accelerometer, gyroscope, and magnetometer readings.
 * The East-North-Up coordinate system is defined as a direct orthonormal basis where:
 *    X points east and is tangential to the ground.
 *    Y points north and is tangential to the ground.
 *    Z points towards the sky and is perpendicular to the ground.
 * The orientation of the phone is represented by the rotation necessary to align the East-North-Up coordinates with the phone's coordinates.
 * That is, applying the rotation to the world frame (X,Y,Z) would align them with the phone coordinates (x,y,z).
 * The rotation can be seen as rotating the phone by an angle theta around an axis rot_axis to go from the reference
 * (East-North-Up aligned) device orientation to the current device orientation. The rotation is encoded as the four unit-less x, y, z, w components of a unit quaternion:
 *    sensors_event_t.data[0] = rot_axis.x*sin(theta/2)
 *    sensors_event_t.data[1] = rot_axis.y*sin(theta/2)
 *    sensors_event_t.data[2] = rot_axis.z*sin(theta/2)
 *    sensors_event_t.data[3] = cos(theta/2)
 * Where:
 * The x, y, and z fields of rot_axis are the East-North-Up coordinates of a unit length vector representing the rotation axis
 * theta is the rotation angle
 *
 * In addition, this sensor reports an estimated heading accuracy:
 *     sensors_event_t.data[4] = estimated_accuracy (in radians)
 *
 */
class RotationVectorLifecycleObserver(
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
         * getDefaultSensor(SENSOR_TYPE_ROTATION_VECTOR) returns a non-wake-up sensor
         */
        _sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
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
            this@RotationVectorLifecycleObserver,
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
         * Underlying physical sensors: Accelerometer, magnetometer, and gyroscope
         *
         * values[0]: Rotation vector component along the x axis (x * sin(θ/2))
         * values[1]: Rotation vector component along the y axis (y * sin(θ/2))
         * values[2]: Rotation vector component along the z axis (z * sin(θ/2))
         * values[3]: Scalar component of the rotation vector ((cos(θ/2)).
         *              (before API 18 - optional value)
         * values[4]: Estimated heading Accuracy (in radians) (-1 if unavailable)
         *              (available since API 18)
         */
        valuesListener?.onSensorValuesChanged(event?.values)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        valuesListener?.onAccuracyValueChanged(accuracy)
    }
}
