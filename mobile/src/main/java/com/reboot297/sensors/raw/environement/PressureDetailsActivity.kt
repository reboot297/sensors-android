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

package com.reboot297.sensors.raw.environement

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import com.reboot297.sensors.BaseActivity
import com.reboot297.sensors.R
import com.reboot297.sensors.databinding.ActivityPressureDetailsBinding

class PressureDetailsActivity : BaseActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var _sensor: Sensor? = null
    private val sensor: Sensor? get() = _sensor
    private lateinit var binding: ActivityPressureDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPressureDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.measureSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                startListening()
            } else {
                stopListening()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        //TODO(Viktor) handle if there are several sensors for one type
        _sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        sensor?.let { displaySensorInfo(it) }

    }

    override fun onStop() {
        super.onStop()
        stopListening()
    }

    private fun startListening() {
        sensorManager.registerListener(
            this@PressureDetailsActivity,
            sensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    private fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    @SuppressLint("StringFormatMatches")
    private fun displaySensorInfo(sensor: Sensor) = with(binding) {
        sensorNameValue.text = sensor.name
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sensorIdValue.text = sensor.id.toString()
            sensorAdditionalInfoValue.text = sensor.isAdditionalInfoSupported.toString()
            sensorIsDynamicValue.text = sensor.isDynamicSensor.toString()
        }
        sensorTypeValue.text = sensor.stringType
        sensorTypeIdValue.text = sensor.type.toString()
        sensorVendorValue.text = sensor.vendor
        sensorVersionValue.text = sensor.version.toString()
        sensorMaxRangeValue.text = getString(R.string.format_unit_pressure, sensor.maximumRange)
        sensorResolutionValue.text = getString(R.string.format_unit_pressure, sensor.resolution)
        sensorPowerValue.text = getString(R.string.format_unit_power, sensor.power)
        sensorMinDelayValue.text = getString(R.string.format_unit_microseconds, sensor.minDelay)
        sensorMaxDelayValue.text = getString(R.string.format_unit_microseconds, sensor.maxDelay)
        sensorFifoMaxValue.text = sensor.fifoMaxEventCount.toString()
        sensorFifoReservedValue.text = sensor.fifoReservedEventCount.toString()
        sensorIsWakeupValue.text = sensor.isWakeUpSensor.toString()
        sensorUnitValue.text = getString(R.string.unit_pressure)
        val reportingModes = resources.getStringArray(R.array.reporting_modes)
        sensorReportingModeValue.text = reportingModes[sensor.reportingMode]

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val levels = resources.getStringArray(R.array.report_rate_levels)
            sensorDirectReportRateLevelValue.text = levels[sensor.highestDirectReportRateLevel]
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.values?.firstOrNull()?.let {
            binding.sensorValueView.text = getString(R.string.format_unit_pressure, it.toString())
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        binding.sensorAccuracyView.text = if (accuracy == -1) {
            getString(R.string.accuracy_no_contact)
        } else {
            resources.getStringArray(R.array.accuracy_values)[accuracy]
        }
    }
}