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

package com.reboot297.sensors.samples.altitude

import android.os.Bundle
import com.reboot297.sensors.BaseSensorDetailsActivity
import com.reboot297.sensors.databinding.ActivityAltitudeBinding
import com.reboot297.sensors.lib.raw.base.AltitudeValuesListener
import com.reboot297.sensors.lib.samples.AltitudeLifecycleObserver

class AltitudeActivity : BaseSensorDetailsActivity(), AltitudeValuesListener {

    override val sensorObserver = AltitudeLifecycleObserver(this, this, this)

    private lateinit var binding: ActivityAltitudeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAltitudeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        with(binding) {
            measureSwitch.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    sensorObserver.startListening()
                } else {
                    sensorObserver.stopListening()
                }
            }
        }
    }

    override fun enableUI(enabled: Boolean) {}

    override fun onValuesChanged(pressure: Float, altitude: Float) {
        binding.sensorValueView.setValue(pressure)
        binding.altitudeValueView.setValue(altitude)
    }
}
