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

class RotationVectorSensorValues : SensorValueSection {
    override fun onDrawValues(view: TextView, values: FloatArray) = with(view) {
        text = values.let { array ->
            StringBuilder()
                .append("x: ").append(array[0]).append("\n")
                .append("y: ").append(array[1]).append("\n")
                .append("z: ").append(array[2]).append("\n")
                .append("scalar: ").append(array[3])
                .toString()
        }
    }
}