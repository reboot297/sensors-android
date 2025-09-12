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

package com.reboot297.sensors.lib.raw

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.LifecycleOwner
import com.reboot297.sensors.lib.raw.base.ActivityListener
import com.reboot297.sensors.lib.raw.base.BaseSensorObserver
import com.reboot297.sensors.lib.raw.base.SensorAvailabilityListener
import com.reboot297.sensors.lib.raw.base.SensorValuesListener

/**
 * An example of reading raw data from an Android proximity sensor.
 *
 * The proximity sensor lets you determine how far away an object is from a device.
 * The proximity sensor is usually used to determine how far away a persons head is from the face of a
 * handset device (for example, when a user is making or receiving a phone call).
 * Most proximity sensors return the absolute distance, in cm, but some return only near and far values.
 *
 * Note: On some device models, the proximity sensor is located underneath the screen,
 * which can cause a blinking dot to appear on the screen if enabled while the screen is on.
 *
 * Note: Some proximity sensors return binary values that represent "near" or "far."
 * In this case, the sensor usually reports its maximum range value in the far state and a lesser value in the near state.
 * Typically, the far value is a value > 5 cm, but this can vary from sensor to sensor
 *
 * Usually defined as a wake-up sensor.
 */
class ProximityLifecycleObserver(
    private val activityListener: ActivityListener,
    private val availabilityListener: SensorAvailabilityListener? = null,
    private val valuesListener: SensorValuesListener? = null,
) : BaseSensorObserver(),
    SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        with(activityListener.getApplicationContext()) {
            sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        /**
         * getDefaultSensor(SENSOR_TYPE_PROXIMITY) returns a wake-up sensor
         */
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
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
            this@ProximityLifecycleObserver,
            sensor,
            SensorManager.SENSOR_DELAY_NORMAL,
        )
    }

    override fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        /**
         * Reporting-mode: On-change
         *
         * values[0]: Proximity sensor distance measured in centimeters
         */
        valuesListener?.onSensorValuesChanged(event?.values)
    }

    override fun onAccuracyChanged(
        sensor: Sensor?,
        accuracy: Int,
    ) {
        valuesListener?.onAccuracyValueChanged(accuracy)
    }
}
