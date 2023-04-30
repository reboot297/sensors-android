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

package com.reboot297.sensors.raw.motion

import android.hardware.Sensor
import android.hardware.TriggerEvent
import android.hardware.TriggerEventListener
import com.reboot297.sensors.BaseSensorActivity
import com.reboot297.sensors.R
import java.util.Date

class SignificantMotionsDetailsActivity : BaseSensorActivity() {

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

    private val triggerEventListener = object : TriggerEventListener() {
        override fun onTrigger(event: TriggerEvent?) {
            binding.sensorValueView.text = getString(R.string.significant_motion_value, Date())
        }
    }

    override fun getUnit() = R.string.unitless

    override fun getSensorType() = Sensor.TYPE_SIGNIFICANT_MOTION
}