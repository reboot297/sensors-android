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

package com.reboot297.sensors.altitude

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.reboot297.sensors.BaseActivity
import com.reboot297.sensors.R
import com.reboot297.sensors.databinding.ActivityAltitudeBinding

class AltitudeActivity : BaseActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var _sensor: Sensor? = null
    private val sensor: Sensor? get() = _sensor
    private lateinit var binding: ActivityAltitudeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAltitudeBinding.inflate(layoutInflater)
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
    }

    override fun onStart() {
        super.onStart()

        _sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        if (sensor == null) {
            AlertDialog.Builder(this)
                .setMessage(R.string.warning_sensor_not_available)
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    finish()
                }.show()
        }
    }

    override fun onStop() {
        super.onStop()
        stopListening()
    }

    fun startListening() {
        sensorManager.registerListener(
            this@AltitudeActivity,
            sensor,
            SensorManager.SENSOR_DELAY_NORMAL,
        )
    }

    fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.values?.firstOrNull()?.let {
            binding.sensorValueView.text = formatTextValue(it, R.string.unit_pressure)
            binding.altitudeValueView.text =
                formatTextValue(getAltitude(it), R.string.unit_altitude)
        }
    }

    private fun formatTextValue(value: Float, @StringRes unit: Int) = StringBuilder()
        .append(value)
        .append(" ")
        .append(getString(unit))
        .toString()

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun getAltitude(pressure: Float) =
        SensorManager.getAltitude(SensorManager.PRESSURE_STANDARD_ATMOSPHERE, pressure)
}
