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

package com.reboot297.sensors.orientation

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.view.Display
import android.view.Surface
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.reboot297.sensors.BaseActivity
import com.reboot297.sensors.R
import com.reboot297.sensors.databinding.ActivityDeviceOrientationBinding

class DeviceOrientationActivity : BaseActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager

    private var _accelerometerSensor: Sensor? = null
    private val accelerometerSensor: Sensor? get() = _accelerometerSensor

    private var _magneticFieldSensor: Sensor? = null
    private val magneticFieldSensor: Sensor? get() = _magneticFieldSensor

    private lateinit var binding: ActivityDeviceOrientationBinding

    private val accelerometerReading = FloatArray(3)
    private val magnetometerReading = FloatArray(3)

    private val rotationMatrix = FloatArray(9)
    private val orientationAngles = FloatArray(3)

    private lateinit var defaultDisplay: Display

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDeviceOrientationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.measureSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                startListening()
            } else {
                stopListening()
            }
        }

        defaultDisplay = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            display!!
        } else {
            @Suppress("DEPRECATION")
            (getSystemService(WINDOW_SERVICE) as WindowManager).defaultDisplay
        }
    }

    override fun onStart() {
        super.onStart()
        _accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        _magneticFieldSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        if (accelerometerSensor == null || magneticFieldSensor == null) {
            AlertDialog.Builder(this).setMessage(R.string.warning_sensor_not_available)
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    finish()
                }.show()
        }
    }

    fun startListening() {
        sensorManager.registerListener(
            this@DeviceOrientationActivity,
            accelerometerSensor,
            SensorManager.SENSOR_DELAY_NORMAL,
            SensorManager.SENSOR_DELAY_UI,
        )

        sensorManager.registerListener(
            this@DeviceOrientationActivity,
            magneticFieldSensor,
            SensorManager.SENSOR_DELAY_NORMAL,
            SensorManager.SENSOR_DELAY_UI,
        )
    }

    override fun onStop() {
        super.onStop()
        stopListening()
    }

    fun stopListening() {
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
            binding.calculatedValueView.text = formatOrientationItems(orientationAngles)
        }
    }

    private fun formatOrientationItems(array: FloatArray): String {
        return StringBuilder()
            .append("Azimuth: ").append(Math.toDegrees(array[0].toDouble()).toFloat()).append("\n")
            .append("Pitch: ").append(Math.toDegrees(array[1].toDouble()).toFloat()).append("\n")
            .append("Roll: ").append(Math.toDegrees(array[2].toDouble())).toString()
    }
}
