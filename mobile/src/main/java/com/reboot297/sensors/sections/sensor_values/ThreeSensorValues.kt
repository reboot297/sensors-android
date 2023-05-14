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

package com.reboot297.sensors.sections.sensor_values

import android.widget.TextView

class ThreeSensorValues(
    private val prefix0: String = "x",
    private val prefix1: String = "y",
    private val prefix2: String = "z",
    private val unit: String = ""
) : SensorValueSection {
    override fun onDrawValues(view: TextView, values: FloatArray) = with(view) {
        text = values.let { array ->
            StringBuilder()
                .append(prefix0).append(": ").append(array[0]).append(" ").append(unit).append("\n")
                .append(prefix1).append(": ").append(array[1]).append(" ").append(unit).append("\n")
                .append(prefix2).append(": ").append(array[2]).append(" ").append(unit)
                .toString()
        }
    }
}