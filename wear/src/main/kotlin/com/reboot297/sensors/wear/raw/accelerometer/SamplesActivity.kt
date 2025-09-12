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

package com.reboot297.sensors.wear.raw.accelerometer

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.reboot297.sensors.databinding.ActivitySamplesAccelerometerBinding
import com.reboot297.sensors.wear.samples.deviceorientation.DeviceOrientationActivity

class SamplesActivity : ComponentActivity() {
    private lateinit var binding: ActivitySamplesAccelerometerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySamplesAccelerometerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.deviceOrientationButton.setOnClickListener {
            startActivity(Intent(this@SamplesActivity, DeviceOrientationActivity::class.java))
        }
    }
}
