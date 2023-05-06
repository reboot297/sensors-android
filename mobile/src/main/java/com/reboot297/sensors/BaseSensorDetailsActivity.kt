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
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.reboot297.sensors.databinding.LayoutSensorInfoBinding

abstract class BaseSensorActivity : BaseActivity() {

    protected fun showSensorNotAvailableDialog() =
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
            sensorMinDelayValue.text = formatTextValue(sensor.minDelay, getString(R.string.unit_microseconds))
            sensorMaxDelayValue.text = formatTextValue(sensor.maxDelay, getString(R.string.unit_microseconds))
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

    protected fun formatTextValue(value: Float) = StringBuilder()
        .append(value)
        .append(" ")
        .append(getUnit())
        .toString()

    protected fun format3Items(array: FloatArray): String {
        val unit = getUnit()
        return StringBuilder()
            .append("x: ").append(array[0]).append(" ").append(unit).append("\n")
            .append("y: ").append(array[1]).append(" ").append(unit).append("\n")
            .append("z: ").append(array[2]).append(" ").append(unit)
            .toString()
    }

    protected fun format6Items(array: FloatArray): String {
        val unit = getUnit()
        return StringBuilder()
            .append("x: ").append(array[0]).append(" ").append(unit).append("\n")
            .append("y: ").append(array[1]).append(" ").append(unit).append("\n")
            .append("z: ").append(array[2]).append(" ").append(unit).append("\n")
            .append("x: ").append(array[3]).append(" ").append(unit).append("\n")
            .append("y: ").append(array[4]).append(" ").append(unit).append("\n")
            .append("z: ").append(array[5]).append(" ").append(unit)
            .toString()
    }

    protected fun formatRotationVector(array: FloatArray): String {
        return StringBuilder()
            .append("x: ").append(array[0]).append("\n")
            .append("y: ").append(array[1]).append("\n")
            .append("z: ").append(array[2]).append("\n")
            .append("scalar: ").append(array[3])
            .toString()
    }

}