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
import android.hardware.SensorManager
import android.hardware.TriggerEvent
import android.hardware.TriggerEventListener
import androidx.lifecycle.LifecycleOwner
import com.reboot297.sensors.lib.raw.base.ActivityListener
import com.reboot297.sensors.lib.raw.base.BaseSensorObserver
import com.reboot297.sensors.lib.raw.base.SensorAvailabilityListener
import com.reboot297.sensors.lib.raw.base.SensorTriggerValuesListener

/**
 * An example of reading raw data from an Android significant motion sensor.
 *
 * A significant motion detector triggers when detecting a significant motion: a motion that might lead to a change
 * in the user location.
 * Examples of such significant motions are:
 * - Walking or biking </br>
 * - Sitting in a moving car, coach, or train
 *
 * Examples of situations that don't trigger significant motion:
 * - Phone in pocket and person isn't moving
 * - Phone is on a table and the table shakes a bit due to nearby traffic or washing machine
 *
 * At the high level, the significant motion detector is used to reduce the power consumption of location determination.
 * When the localization algorithms detect that the device is static, they can switch to a low-power mode,
 * where they rely on significant motion to wake the device up when the user is changing location.
 */

class SignificantMotionLifecycleObserver(
    private val activityListener: ActivityListener,
    private val availabilityListener: SensorAvailabilityListener? = null,
    private val triggerValueListener: SensorTriggerValuesListener? = null,
) : BaseSensorObserver() {
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
         * getDefaultSensor(SENSOR_TYPE_SIGNIFICANT_MOTION) returns a wake-up sensor
         */
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION)
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
        sensor?.let {
            sensorManager.requestTriggerSensor(triggerEventListener, it)
        }
    }

    override fun stopListening() {
        sensor?.let {
            sensorManager.cancelTriggerSensor(triggerEventListener, sensor)
        }
    }

    private val triggerEventListener =
        object : TriggerEventListener() {
            override fun onTrigger(event: TriggerEvent?) {
                /**
                 *  Reporting-mode: One-shot
                 *  Underlying physical sensor: Accelerometer (or another as long as low power)
                 *
                 * value[0] = 1.0 when the sensor triggers. 1.0 is the only allowed value.
                 */

                event?.let { triggerValueListener?.onTrigger(it) }
            }
        }
}
