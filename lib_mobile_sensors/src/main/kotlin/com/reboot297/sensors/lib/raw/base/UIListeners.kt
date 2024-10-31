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

package com.reboot297.sensors.lib.raw.base

import android.content.Context
import android.hardware.Sensor
import android.hardware.TriggerEvent
import android.view.Display

interface ActivityListener {
    fun getApplicationContext(): Context
    fun requestPermissions(permission: String) = Unit
    fun getDefaultDisplay(): Display? = null
}

interface SensorAvailabilityListener {
    fun onSensorAvailable(sensor: Sensor)
    fun onSensorNotAvailable()
}

interface SensorValuesListener {
    fun onSensorValuesChanged(values: FloatArray?)
    fun onAccuracyValueChanged(accuracy: Int)
}

interface SensorTriggerValuesListener {
    fun onTrigger(event: TriggerEvent)
}

interface AltitudeValuesListener {
    fun onValuesChanged(pressure: Float, altitude: Float)
}

interface MetalDetectionValuesListener {
    fun onValuesChanged(rawValues: FloatArray?, value: Double)
}

interface DeviceOrientationValuesListener {
    fun onValuesChanged(values: FloatArray)
}
