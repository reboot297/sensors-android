/*
 * Copyright (c) 2023. Viktor Pop
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.reboot297.sensors

import android.annotation.SuppressLint
import android.hardware.Sensor
import android.os.Build
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.reboot297.sensors.databinding.ActivityDetailsBinding
import com.reboot297.sensors.databinding.LayoutSensorInfoBinding
import com.reboot297.sensors.sections.SectionUI

abstract class BaseSensorDetailsActivity : BaseActivity() {

    protected lateinit var binding: ActivityDetailsBinding

    protected lateinit var ui: SectionUI
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        ui = createSectionsUI()

        with(binding) {
            measureSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    sensorDataLayout.root.isVisible = true
                    startListening()
                } else {
                    stopListening()
                }
            }

            sensorDataLabelView.setOnClickListener {
                sensorDataLayout.root.isVisible = !sensorDataLayout.root.isVisible
            }

            sensorInfoLabelView.setOnClickListener {
                sensorInfoLayout.root.isVisible = !sensorInfoLayout.root.isVisible
            }

            sensorDescriptionLabelView.setOnClickListener {
                sensorDescriptionView.isVisible = !sensorDescriptionView.isVisible
            }

            ui.displayDescription(sensorDescriptionView)

            samplesLabelView.setOnClickListener {
                samplesLayout.isVisible = !samplesLayout.isVisible
            }

            ui.displaySamples(samplesLayout)
        }
    }

    protected abstract fun startListening()

    protected abstract fun stopListening()

    protected abstract fun createSectionsUI(): SectionUI

    protected fun displaySensorValues(values: FloatArray) {
        ui.displaySensorValue(binding.sensorDataLayout.sensorValueView, values)
    }

    override fun onStop() {
        super.onStop()
        stopListening()
    }

    protected fun showSensorNotAvailableDialog(): AlertDialog =
        AlertDialog.Builder(this)
            .setMessage(R.string.warning_sensor_not_available)
            .setPositiveButton(android.R.string.ok) { _, _ -> finish() }
            .setCancelable(false)
            .show()

    @SuppressLint("StringFormatMatches")
    protected fun displaySensorInfo(sensor: Sensor, sensorInfoBinding: LayoutSensorInfoBinding) =
        with(sensorInfoBinding) {
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
            sensorMaxRangeValue.text = formatTextValue(sensor.maximumRange, getUnit())
            sensorResolutionValue.text = formatTextValue(sensor.resolution, getUnit())
            sensorPowerValue.text = formatTextValue(sensor.power, getString(R.string.unit_power))
            sensorMinDelayValue.text =
                formatTextValue(sensor.minDelay, getString(R.string.unit_microseconds))
            sensorMaxDelayValue.text =
                formatTextValue(sensor.maxDelay, getString(R.string.unit_microseconds))
            sensorFifoMaxValue.text = sensor.fifoMaxEventCount.toString()
            sensorFifoReservedValue.text = sensor.fifoReservedEventCount.toString()
            sensorIsWakeupValue.text = sensor.isWakeUpSensor.toString()
            sensorUnitValue.text = getUnit()
            val reportingModes = resources.getStringArray(R.array.reporting_modes)
            sensorReportingModeValue.text = reportingModes[sensor.reportingMode]

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val levels = resources.getStringArray(R.array.report_rate_levels)
                sensorDirectReportRateLevelValue.text = levels[sensor.highestDirectReportRateLevel]
            }
        }

    @StringRes
    protected abstract fun getUnitResId(): Int

    private fun getUnit() = getString(getUnitResId())

    private fun formatTextValue(value: Int, unit: String) = StringBuilder()
        .append(value)
        .append(" ")
        .append(unit)
        .toString()

    private fun formatTextValue(value: Float, unit: String) = StringBuilder()
        .append(value)
        .append(" ")
        .append(unit)
        .toString()

}
