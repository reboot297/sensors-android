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

package com.reboot297.sensors.wear.samples

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import com.reboot297.sensors.R
import com.reboot297.sensors.databinding.ActivitySamplesBinding
import com.reboot297.sensors.wear.samples.altitude.AltitudeActivity
import com.reboot297.sensors.wear.samples.deviceorientation.DeviceOrientationActivity
import com.reboot297.sensors.wear.samples.metaldetection.MetalDetectionActivity

class SamplesActivity : ComponentActivity() {
    private lateinit var binding: ActivitySamplesBinding

    private val map =
        mapOf<Int, Class<*>>(
            R.id.altitude_button to AltitudeActivity::class.java,
            R.id.device_orientation_button to DeviceOrientationActivity::class.java,
            R.id.metal_detection_button to MetalDetectionActivity::class.java,
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySamplesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        map.keys
            .asSequence()
            .map { findViewById<View>(it) }
            .forEach { it.setOnClickListener { view -> openDetails(view) } }
    }

    private fun openDetails(view: View) {
        startActivity(Intent(this, map[view.id]))
    }
}
