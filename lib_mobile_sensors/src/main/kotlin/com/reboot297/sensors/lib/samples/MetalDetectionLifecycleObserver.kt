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
import androidx.lifecycle.LifecycleOwner
import com.reboot297.sensors.lib.raw.base.ActivityListener
import com.reboot297.sensors.lib.raw.base.BaseSensorObserver
import com.reboot297.sensors.lib.raw.base.MetalDetectionValuesListener
import com.reboot297.sensors.lib.raw.base.SensorAvailabilityListener
import kotlin.math.sqrt

class MetalDetectionLifecycleObserver(
    private val activityListener: ActivityListener,
    private val availabilityListener: SensorAvailabilityListener? = null,
    private val valuesListener: MetalDetectionValuesListener? = null,
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
        _sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
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
            this@MetalDetectionLifecycleObserver,
            sensor,
            SensorManager.SENSOR_DELAY_NORMAL,
        )
    }

    override fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.values?.let {
            val result = sqrt((it[0] * it[0] + it[1] * it[1] + it[2] * it[2]).toDouble())
            valuesListener?.onValuesChanged(it, result)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
