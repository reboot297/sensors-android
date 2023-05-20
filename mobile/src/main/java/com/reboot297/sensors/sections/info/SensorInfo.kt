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

package com.reboot297.sensors.sections.info

import android.content.Context
import android.hardware.Sensor
import android.os.Build
import com.reboot297.sensors.R
import com.reboot297.sensors.databinding.LayoutSensorInfoBinding

class SensorInfo(
    applicationContext: Context,
    private val unit: String = ""
) : InfoSection {

    private val unitPower = applicationContext.getString(R.string.unit_power)
    private val unitMicroseconds = applicationContext.getString(R.string.unit_microseconds)
    private val reportingModes =
        applicationContext.resources.getStringArray(R.array.reporting_modes)
    private val directReportRateLevels =
        applicationContext.resources.getStringArray(R.array.report_rate_levels)


    override fun displaySensorInfo(sensor: Sensor, sensorInfoBinding: LayoutSensorInfoBinding) =
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

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                sensorDirectReportRateLevelValue.text =
                    directReportRateLevels[sensor.highestDirectReportRateLevel]
            }
        }

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