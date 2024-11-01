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

package com.reboot297.sensors.wear.samples.deviceorientation

import android.os.Bundle
import com.reboot297.sensors.R
import com.reboot297.sensors.databinding.ActivitySampleBinding
import com.reboot297.sensors.wear.raw.BaseRawSensorActivity

class DeviceOrientationActivity : BaseRawSensorActivity() {

    private lateinit var binding: ActivitySampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            labelView.setText(R.string.title_device_orientation)
            valuesButton.setOnClickListener { openSection(ValuesActivity::class.java) }
            descriptionButton.setOnClickListener { openDetails(R.string.orientation_description_value) }
        }
    }
}
