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

package com.reboot297.sensors.lib.samples

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.view.Display
import android.view.Surface
import androidx.lifecycle.LifecycleOwner
import com.reboot297.sensors.lib.raw.base.ActivityListener
import com.reboot297.sensors.lib.raw.base.BaseSensorObserver
import com.reboot297.sensors.lib.raw.base.DeviceOrientationValuesListener
import com.reboot297.sensors.lib.raw.base.SensorAvailabilityListener

class DeviceOrientationLifecycleObserver(
    private val activityListener: ActivityListener,
    private val availabilityListener: SensorAvailabilityListener? = null,
    private val valuesListener: DeviceOrientationValuesListener? = null,
) : BaseSensorObserver(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var _accelerometerSensor: Sensor? = null
    private val accelerometerSensor: Sensor? get() = _accelerometerSensor

    private var _magneticFieldSensor: Sensor? = null
    private val magneticFieldSensor: Sensor? get() = _magneticFieldSensor

    private val accelerometerReading = FloatArray(3)
    private val magnetometerReading = FloatArray(3)

    private val rotationMatrix = FloatArray(9)
    private val orientationAngles = FloatArray(3)

    private lateinit var defaultDisplay: Display

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        with(activityListener.getApplicationContext()) {
            sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        }

        defaultDisplay = activityListener.getDefaultDisplay() ?: error("Cannot get default display")
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        _accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        _magneticFieldSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        availabilityListener?.let {
            if (accelerometerSensor != null) {
                it.onSensorAvailable(accelerometerSensor!!)
            } else {
                it.onSensorNotAvailable()
            }

            if (magneticFieldSensor != null) {
                it.onSensorAvailable(magneticFieldSensor!!)
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
            this@DeviceOrientationLifecycleObserver,
            accelerometerSensor,
            SensorManager.SENSOR_DELAY_NORMAL,
            SensorManager.SENSOR_DELAY_UI,
        )

        sensorManager.registerListener(
            this@DeviceOrientationLifecycleObserver,
            magneticFieldSensor,
            SensorManager.SENSOR_DELAY_NORMAL,
            SensorManager.SENSOR_DELAY_UI,
        )
    }

    override fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        when (event?.sensor?.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                System.arraycopy(
                    event.values,
                    0,
                    accelerometerReading,
                    0,
                    accelerometerReading.size,
                )
            }

            Sensor.TYPE_MAGNETIC_FIELD -> {
                System.arraycopy(event.values, 0, magnetometerReading, 0, magnetometerReading.size)
            }

            else -> return
        }
        updateOrientationAngles()
        valuesListener?.onValuesChanged(orientationAngles)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun updateOrientationAngles() {
        // Update rotation matrix, which is needed to update orientation angles.
        val success = SensorManager.getRotationMatrix(
            rotationMatrix,
            null,
            accelerometerReading,
            magnetometerReading,
        )
        if (success) {
            // Remap the matrix based on current device/activity rotation.
            var rotationMatrixAdjusted = FloatArray(9)
            when (defaultDisplay.rotation) {
                Surface.ROTATION_0 -> rotationMatrixAdjusted = rotationMatrix.clone()
                Surface.ROTATION_90 -> SensorManager.remapCoordinateSystem(
                    rotationMatrix,
                    SensorManager.AXIS_Y,
                    SensorManager.AXIS_MINUS_X,
                    rotationMatrixAdjusted,
                )

                Surface.ROTATION_180 -> SensorManager.remapCoordinateSystem(
                    rotationMatrix,
                    SensorManager.AXIS_MINUS_X,
                    SensorManager.AXIS_MINUS_Y,
                    rotationMatrixAdjusted,
                )

                Surface.ROTATION_270 -> SensorManager.remapCoordinateSystem(
                    rotationMatrix,
                    SensorManager.AXIS_MINUS_Y,
                    SensorManager.AXIS_X,
                    rotationMatrixAdjusted,
                )
            }

            SensorManager.getOrientation(rotationMatrixAdjusted, orientationAngles)
        }
    }
}
