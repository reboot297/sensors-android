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

package com.reboot297.sensors.sections

import android.hardware.Sensor
import android.widget.LinearLayout
import android.widget.TextView
import com.reboot297.sensors.databinding.LayoutSensorInfoBinding
import com.reboot297.sensors.sections.accuracy.AccuracyValueSection
import com.reboot297.sensors.sections.description.DescriptionSection
import com.reboot297.sensors.sections.info.InfoSection
import com.reboot297.sensors.sections.samples.Samples
import com.reboot297.sensors.sections.samples.SamplesSection
import com.reboot297.sensors.sections.sensor_values.SensorValueSection

class SectionUIImpl constructor(
    private val sensorValue: SensorValueSection,
    private val accuracyValue: AccuracyValueSection,
    private val sensorInfo: InfoSection,
    private val description: DescriptionSection,
    private val sample: SamplesSection = Samples(emptyList())
) : SectionUI {
    override fun displaySensorValue(view: TextView, values: FloatArray) {
        sensorValue.onDrawValues(view, values)
    }

    override fun displaySensorAccuracy(view: TextView, value: Int) {
        accuracyValue.onDrawValues(view, value)
    }

    override fun displaySensorInfo(sensor: Sensor, sensorInfoBinding: LayoutSensorInfoBinding) {
        sensorInfo.displaySensorInfo(sensor, sensorInfoBinding)
    }

    override fun displayDescription(view: TextView) {
        description.onDrawValues(view)
    }

    override fun displaySamples(layout: LinearLayout) {
        sample.onDrawValues(layout)
    }
}