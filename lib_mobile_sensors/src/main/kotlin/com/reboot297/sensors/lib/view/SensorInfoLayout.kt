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

package com.reboot297.sensors.lib.view

import android.content.Context
import android.hardware.Sensor
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.reboot297.sensors.lib.R
import com.reboot297.sensors.lib.databinding.LayoutSensorInfoBinding

class SensorInfoLayout(
    context: Context,
    attrs: AttributeSet?,
) : FrameLayout(context, attrs) {
    private val unitPower: String
    private val unitMicroseconds: String
    private val reportingModes: Array<String>
    private val directReportRateLevels: Array<String>

    private val binding = LayoutSensorInfoBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        unitPower = context.getString(R.string.unit_power)
        unitMicroseconds = context.getString(R.string.unit_microseconds)
        reportingModes =
            context.resources.getStringArray(R.array.reporting_modes)
        directReportRateLevels =
            context.resources.getStringArray(R.array.report_rate_levels)
    }

    fun setInfo(
        sensor: Sensor,
        unit: String,
    ) = with(binding) {
        sensorNameValue.text = sensor.name
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            sensorIdValue.text = sensor.id.toString()
            sensorAdditionalInfoValue.text = sensor.isAdditionalInfoSupported.toString()
            sensorIsDynamicValue.text = sensor.isDynamicSensor.toString()
        }
        sensorTypeValue.text = sensor.stringType
        sensorTypeIdValue.text = sensor.type.toString()
        sensorVendorValue.text = sensor.vendor
        sensorVersionValue.text = sensor.version.toString()
        sensorMaxRangeValue.text = formatTextValue(sensor.maximumRange, unit)
        sensorResolutionValue.text = formatTextValue(sensor.resolution, unit)
        sensorPowerValue.text = formatTextValue(sensor.power, unitPower)
        sensorMinDelayValue.text = formatTextValue(sensor.minDelay, unitMicroseconds)
        sensorMaxDelayValue.text = formatTextValue(sensor.maxDelay, unitMicroseconds)
        sensorFifoMaxValue.text = sensor.fifoMaxEventCount.toString()
        sensorFifoReservedValue.text = sensor.fifoReservedEventCount.toString()
        sensorIsWakeupValue.text = sensor.isWakeUpSensor.toString()
        sensorUnitValue.text = unit

        sensorReportingModeValue.text = reportingModes[sensor.reportingMode]

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            sensorDirectReportRateLevelValue.text =
                directReportRateLevels[sensor.highestDirectReportRateLevel]
        }
    }

    private fun formatTextValue(
        value: Int,
        unit: String,
    ) = StringBuilder()
        .append(value)
        .append(" ")
        .append(unit)
        .toString()

    private fun formatTextValue(
        value: Float,
        unit: String,
    ) = StringBuilder()
        .append(value)
        .append(" ")
        .append(unit)
        .toString()
}
